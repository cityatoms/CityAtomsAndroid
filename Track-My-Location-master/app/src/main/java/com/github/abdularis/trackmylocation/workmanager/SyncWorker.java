package com.github.abdularis.trackmylocation.workmanager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.github.abdularis.trackmylocation.BaseApplication;
import com.github.abdularis.trackmylocation.R;
import com.github.abdularis.trackmylocation.common.IPreferencesKeys;
import com.github.abdularis.trackmylocation.dagger.ApiInterface;
import com.github.abdularis.trackmylocation.dashboard.MainActivity;
import com.github.abdularis.trackmylocation.network.model.LocationSymptomRequest;
import com.github.abdularis.trackmylocation.network.model.LoginResponse;
import com.github.abdularis.trackmylocation.startupui.AnonymousLogin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import freemarker.template.utility.Constants;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SyncWorker extends Worker {

    private final static String TAG = "City Atoms";

    private static final String WORK_RESULT = "work_result";

    @Inject
    ApiInterface apiInterface;
    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        ((BaseApplication) context.getApplicationContext()).getApiComponent().inject(this);
    }
    @SuppressLint("CheckResult")
    @NonNull
    @Override
    public Result doWork() {
        Data taskData = getInputData();
        String taskDataString = taskData.getString(MainActivity.MESSAGE_STATUS);
        final SharedPreferences preferences =
                getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(),
                        Context.MODE_PRIVATE);
        String token = preferences.
                getString(IPreferencesKeys.ACCESS_TOKEN, String.valueOf(Constants.EMPTY_STRING));
        //TODO change to user token and get data from  database //Remove local file
        apiInterface.locationSymptomSync(token,getLocationSymptomRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    showNotification("WorkManager", taskDataString != null ? taskDataString :
                            "Message has been Sent");

                }, throwable -> {
                    showNotification("WorkManager", taskDataString != null ? taskDataString :
                            "failed to Sync");

                });

        Data outputData = new Data.Builder().putString(WORK_RESULT, "Jobs Finished").build();
        return Result.success(outputData);
    }
    private void showNotification(String task, String desc) {
        NotificationManager manager = (NotificationManager) getApplicationContext().
                getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "task_channel";
        String channelName = "task_name";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),
                channelId)
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher);
        manager.notify(1, builder.build());
    }

    /* Convert JSON String to BaseWatch Model via GSON */
//    public List<LocationSymptomRequest> getLocationSymptomRequest() {
//        String jsonString = getAssetsJSON("file_name.json");
//        Log.d(TAG, "Json: " + jsonString);
//        Gson gson = new Gson();
//        List<LocationSymptomRequest> list = gson.fromJson(jsonString,
//                new TypeToken<List<LocationSymptomRequest>>(){}.getType());
//        return list;
//    }

    public List<LocationSymptomRequest> getLocationSymptomRequest() {
        List<LocationSymptomRequest> list = new ArrayList<>();
        Gson gson = new Gson();
        return list;
    }

    /* Get File in Assets Folder */
    public String getAssetsJSON(String fileName) {
        String json = null;
        try {
            InputStream inputStream = getApplicationContext().getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
}