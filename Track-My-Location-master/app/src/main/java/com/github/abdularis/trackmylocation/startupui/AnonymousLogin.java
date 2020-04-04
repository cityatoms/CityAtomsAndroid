package com.github.abdularis.trackmylocation.startupui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.github.abdularis.trackmylocation.BaseApplication;
import com.github.abdularis.trackmylocation.R;
import com.github.abdularis.trackmylocation.common.IPreferencesKeys;
import com.github.abdularis.trackmylocation.common.Util;
import com.github.abdularis.trackmylocation.dagger.ApiInterface;
import com.github.abdularis.trackmylocation.dashboard.BaseActivity;
import com.github.abdularis.trackmylocation.dashboard.MainActivity;
import com.github.abdularis.trackmylocation.network.model.LoginRequest;
import com.github.abdularis.trackmylocation.network.model.LoginResponse;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AnonymousLogin extends BaseActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    String device_unique_id = "";
    String timeZone = "";
    String countryCodeValue = "";
    // request code untuk login
    private static final int RC_LOGIN = 123;

    @BindView(R.id.checkbox)
    CheckBox checkAgree;
    @Inject
    ApiInterface apiInterface;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth firebaseAuth;
    private SharedPreferences preferences;
    // private ApiService apiService;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous);
        ButterKnife.bind(this);

        ((BaseApplication) getApplication()).getApiComponent().inject(this);
        preferences = BaseApplication.getBaseApplication().getPreferences();
        Util.getInstance().hideKeyboard(this);
        TextView txtAgreement = findViewById(R.id.txt_Agreement);
        SpannableString ss = new SpannableString(getString(R.string.agreement));
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(AnonymousLogin.this, "1", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AnonymousLogin.this, "2", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_LOGIN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                goToMainActivity();
            } else {
                if (response == null) {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @SuppressLint("HardwareIds")
    @OnClick(R.id.btn_signup)
    public void onClickSignUp() {
        if (checkAgree.isChecked()) {
            device_unique_id = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            countryCodeValue = checkCountry();
            timeZone = getTimeZone();
            goToMainActivity();
        }
        else
            Toast.makeText(this, "Please agree to the terms and conditions",
                    Toast.LENGTH_SHORT).show();
    }

    private void goToMainActivity() {
        preferences.edit().putString(IPreferencesKeys.USER_ID, device_unique_id).apply();
        preferences.edit().putString(IPreferencesKeys.TIME_ZONE, timeZone).apply();
        preferences.edit().putString(IPreferencesKeys.COUNTRY, countryCodeValue).apply();
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i("TAG", "onRequestPermissionResult");
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            if (grantResults.length <= 0) {
                Toast.makeText(this, "Access Denied, Please try again!", Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                device_unique_id = Settings.Secure.getString(this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                LoginRequest loginRequest = new LoginRequest
                        .Builder()
                        .withInstanceId(device_unique_id)
                        .withCountryCode(getResources().getConfiguration().locale.getCountry())
                        .withTimeZone(TimeZone.getDefault().getDisplayName())
                        .build();
                disposable.add(
                        apiInterface
                                .login(loginRequest)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableSingleObserver<LoginResponse>() {
                                    @Override
                                    public void onSuccess(LoginResponse user) {
                                        checkCountry();
                                        timeZone = getTimeZone();
                                        goToMainActivity();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Toast.makeText(AnonymousLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();

                                    }
                                }));
            } else {
                finish();
            }
        }
    }

    private String checkCountry() {
        TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (telMgr == null)
            return "";
        int simState = telMgr.getSimState();
        if (simState == TelephonyManager.SIM_STATE_READY) {
            String countryCode = telMgr.getNetworkCountryIso();
            Locale loc = new Locale("", countryCode);
            return loc.getDisplayCountry();
        }
        return "";
    }

    private String getTimeZone() {
        Calendar aGMTCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        aGMTCalendar.getTime();
        return (aGMTCalendar.getTime()).toString();
    }
    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_PHONE_STATE);
        if (shouldProvideRationale) {
            ActivityCompat.requestPermissions(AnonymousLogin.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        } else {
            ActivityCompat.requestPermissions(AnonymousLogin.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
    }

    //Region onclicks
    @OnClick(R.id.txt2)
    public void OnClickRegisterPage() {
        Intent i = new Intent(AnonymousLogin.this, RegisterAccount.class);
        startActivity(i);
        this.finish();
    }

    @OnClick(R.id.txt1)
    public void OnClickLoginPage() {
        Intent i = new Intent(AnonymousLogin.this, StartupActivity.class);
        startActivity(i);
        this.finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
