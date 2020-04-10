package com.foribus.cityatoms.database.datapoints;

import android.content.Context;
import android.location.Location;

import com.foribus.cityatoms.BaseApplication;
import com.foribus.cityatoms.Enitity.LoginEntity;
import com.foribus.cityatoms.common.SyncService;
import com.foribus.cityatoms.database.DatabaseRepositoryManager;
import com.foribus.cityatoms.database.symptoms.SymptomsScoreEntity;
import com.foribus.cityatoms.network.RetrofitClient;
import com.foribus.cityatoms.network.model.LocationSymptomRequest;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class DataPointRepository extends SyncService<DataPointEntity> {

    // 1 minute
    private static final long SYNC_INTERVAL = 1000 * 60;

    private DataPointDao dataPointDao;
    private Context context;

    public DataPointRepository(Context context) {
        super(SYNC_INTERVAL, Executors.newCachedThreadPool());
        this.context = context;
        dataPointDao = DataPointDatabase.getDatabase(context).dataPointDao();
    }

    public void saveDataPoint(Location location) {
        if (location.getLatitude() == 0 && location.getLongitude() == 0)
            return;

        Date date = new Date();
        DataPointEntity entity = new DataPointEntity();
        entity.setLat(location.getLatitude());
        entity.setLon(location.getLongitude());
        entity.setEpochTime((date.getTime() / 1000));
        entity.setTime(DateFormat.getDateTimeInstance().format(date));
        entity.setSyncStatus(SyncStatus.PENDING);
        add(entity);
    }

    @Override
    protected void add(DataPointEntity entity) {
        super.add(entity);
    }

    @Override
    protected void onEntityAdd(DataPointEntity entity) {
        SymptomsScoreEntity symptomsScoreEntity = DatabaseRepositoryManager.getInstance(context).symptomsScoreRepository().getLatestSymptomScoreInfo();
        if (symptomsScoreEntity != null)
            entity.setSymptomsScoreId(symptomsScoreEntity.getId());
        else
            entity.setSymptomsScoreId(0);
        dataPointDao.insert(entity);
    }

    @Override
    protected void sync() {
        String instance_id = BaseApplication.getBaseApplication().getFirebaseInstanceId();

        if (instance_id != null) {
            Timber.d("Syncing data points");

            List<DataPointEntity> unProcessedData = dataPointDao.getUnprocessedDataPoints();
            for (DataPointEntity entity : unProcessedData) {
                entity.setSyncStatus(SyncStatus.IN_PROGRESS);
            }
            dataPointDao.update(unProcessedData);

            List<DataPointEntity> inProcessData = dataPointDao.getInProcessDataPoints();
            if (inProcessData.size() > 0) {
                List<LocationSymptomRequest> data = new ArrayList<>(inProcessData.size());
                for (DataPointEntity dataPointEntity : inProcessData) {
                    SymptomsScoreEntity symptomsScoreEntity = DatabaseRepositoryManager.getInstance(context).symptomsScoreRepository().getSymptomScoreInfo(dataPointEntity.getId());
                    LocationSymptomRequest request = LocationSymptomRequest.instance(dataPointEntity, symptomsScoreEntity);
                    request.setInstanceId(instance_id);
                    data.add(request);
                }

                Call<LoginEntity> call = RetrofitClient.getApiService().postBatchDataPoints("test", instance_id, data);
                call.enqueue(new Callback<LoginEntity>() {
                    @Override
                    public void onResponse(Call<LoginEntity> call, Response<LoginEntity> response) {
                        Timber.d("data points post response: %s", response.isSuccessful());
                        if (response.isSuccessful()) {
                            for (DataPointEntity dataPointEntity : inProcessData) {
                                dataPointEntity.setSyncStatus(SyncStatus.SYNCED);
                            }
                            Timber.d("%d data points uploaded", inProcessData.size());
                        } else {
                            // Change back to step 0 if failed
                            for (DataPointEntity dataPointEntity : inProcessData) {
                                dataPointEntity.setSyncStatus(SyncStatus.PENDING);
                            }
                            Timber.d("%d data points failed to upload", inProcessData.size());
                        }
                        dataPointDao.update(inProcessData);

                        finishSyncing();
                    }

                    @Override
                    public void onFailure(Call<LoginEntity> call, Throwable t) {
                        Timber.e(t);
                    }
                });
            } else {
                Timber.d("No data points to upload");
                finishSyncing();
            }
        } else {
            stopSyncing();
        }
    }
}
