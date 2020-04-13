package com.foribus.cityatoms.startupui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.foribus.cityatoms.BaseApplication;
import com.foribus.cityatoms.Enitity.LoginEntity;
import com.foribus.cityatoms.R;
import com.foribus.cityatoms.common.IPreferencesKeys;
import com.foribus.cityatoms.common.Util;
import com.foribus.cityatoms.dashboard.BaseActivity;
import com.foribus.cityatoms.firebase.FirebaseAuthHelper;
import com.foribus.cityatoms.network.RetrofitClient;
import com.foribus.cityatoms.network.model.LoginRequest;
import com.google.firebase.auth.FirebaseUser;

import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class AnonymousLogin extends BaseActivity {
    String device_unique_id = "";
    String timeZone = "";
    String countryCodeValue = "";
    @BindView(R.id.checkbox)
    CheckBox checkAgree;

    private SharedPreferences preferences;
    private FirebaseAuthHelper firebaseAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous);
        ButterKnife.bind(this);

        preferences = BaseApplication.getBaseApplication().getPreferences();
        firebaseAuthHelper = new FirebaseAuthHelper(this);

        Util.getInstance().hideKeyboard(this);
        Timber.d("onCreate: ");
        TextView txtAgreement = findViewById(R.id.txt_Agreement);
        SpannableString ss = new SpannableString(getString(R.string.agreement));
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                String url = "https://www.cityatoms.com/toc";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                Toast.makeText(AnonymousLogin.this, "Terms page", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.colorTextBlue));
                ds.setUnderlineText(false);
            }
        };

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                String url = "https://www.cityatoms.com/toc";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                Toast.makeText(AnonymousLogin.this, "Services page", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.colorTextBlue));
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan1, 26, 42, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2, 47, 61, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        txtAgreement.setText(ss);
    }

    @SuppressLint("HardwareIds")
    @OnClick(R.id.btn_signup)
    public void onClickSignUp() {
        if (checkAgree.isChecked()) {
            showProgressBar("" +
                    "Please wait");
            generateFirebaseInstanceId();
        } else
            Toast.makeText(this, R.string.terms_and_conditions,
                    Toast.LENGTH_SHORT).show();
    }

    public void generateFirebaseInstanceId() {
        firebaseAuthHelper.generateFirebaseInstanceId(new FirebaseAuthHelper.FirebaseAuthCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                countryCodeValue = checkCountry();
                timeZone = getTimeZone();

                // Get new Instance ID token
                device_unique_id = user.getUid();

                LoginRequest loginRequest = new LoginRequest.Builder().withCountryCode(countryCodeValue).withTimeZone(timeZone).withInstanceId(device_unique_id).build();
                Call<LoginEntity> call = RetrofitClient.getApiService().callLogin("test", loginRequest);
                call.enqueue(new Callback<LoginEntity>() {
                    @Override
                    public void onResponse(Call<LoginEntity> call, Response<LoginEntity> response) {
                        Timber.d("callLogin API response code %d, status %s", response.code(), response.isSuccessful());

                        if (response.isSuccessful()) {
                            hideProgressBar();
                            preferences.edit().putString(IPreferencesKeys.ID, response.body().getId()).apply();
                            preferences.edit().putString(IPreferencesKeys.USER_ID, device_unique_id).apply();
                            preferences.edit().putString(IPreferencesKeys.TIME_ZONE, timeZone).apply();
                            preferences.edit().putString(IPreferencesKeys.COUNTRY, countryCodeValue).apply();
                            goToMainActivity();
                        } else if (response.code() == 409) {
                            hideProgressBar();
                            Timber.d("Login conflict with user %s, trying to fetch user info", device_unique_id);
                            RetrofitClient.getApiService().getUser("test", device_unique_id).enqueue(new Callback<LoginEntity>() {
                                @Override
                                public void onResponse(Call<LoginEntity> call, Response<LoginEntity> response) {
                                    Timber.d("getUser API response code %d, status %s", response.code(), response.isSuccessful());
                                    if (response.isSuccessful()) {
                                        preferences.edit().putString(IPreferencesKeys.ID, response.body().getId()).apply();
                                        preferences.edit().putString(IPreferencesKeys.USER_ID, device_unique_id).apply();
                                        preferences.edit().putString(IPreferencesKeys.TIME_ZONE, timeZone).apply();
                                        preferences.edit().putString(IPreferencesKeys.COUNTRY, countryCodeValue).apply();
                                        goToMainActivity();
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginEntity> call, Throwable t) {
                                    hideProgressBar();
                                    Timber.e(t);
                                }
                            });
                        } else {
                            hideProgressBar();
                            Timber.i("Log in failed with response code %d", response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginEntity> call, Throwable t) {
                        hideProgressBar();
                        Timber.e(t);
                    }
                });
            }

            @Override
            public void onFail() {
                hideProgressBar();
                Timber.e("Firebase anonymous login failed");
            }
        });
    }

    private void goToMainActivity() {
        Intent i = new Intent(this, GetSymptoms.class);
        startActivity(i);
        finish();
    }

    private String checkCountry() {
        TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (telMgr == null)
            return "UNKNOWN";
        int simState = telMgr.getSimState();
        if (simState == TelephonyManager.SIM_STATE_READY) {
            String countryCode = telMgr.getNetworkCountryIso();
            return countryCode.toUpperCase();

        }
        return "UNKNOWN";
    }

    private String getTimeZone() {
        return TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT);
    }
}
