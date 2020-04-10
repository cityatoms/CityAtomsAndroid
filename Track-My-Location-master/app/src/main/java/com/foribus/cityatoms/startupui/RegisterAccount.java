package com.foribus.cityatoms.startupui;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.foribus.cityatoms.R;
import com.foribus.cityatoms.common.Util;
import com.foribus.cityatoms.dashboard.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterAccount extends BaseActivity {

    @BindView(R.id.txt_username)
    EditText edUsername;
    @BindView(R.id.txt_password)
    EditText edPass;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        Util.getInstance().hideKeyboard(this);
        TextView txtAgreement = findViewById(R.id.txt_Agreement);
        SpannableString ss = new SpannableString(getString(R.string.agreement));
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(RegisterAccount.this, "1", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(RegisterAccount.this, "2", Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.btn_signup)
    public void OnClickSignUp() {
        createUserAccount();
    }

    @OnClick(R.id.txt1)
    public void OnClickBack() {
        goToLogin();
    }

    @OnClick(R.id.txt2)
    public void OnClickAnonimus() {
        goToAnonymus();
    }

    public void createUserAccount() {
        final String createName = edUsername.getText().toString().trim();
        final String password = edPass.getText().toString().trim();

        if (TextUtils.isEmpty(createName)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(createName, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(RegisterAccount.this,
                                    "Registered Successfully!",
                                    Toast.LENGTH_SHORT).show();
                            RegisterAccount.this.finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterAccount.this,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void goToAnonymus() {
        Intent i = new Intent(RegisterAccount.this, AnonymousLogin.class);
        startActivity(i);
        this.finish();
    }

    private void goToLogin() {
        Intent i = new Intent(RegisterAccount.this, StartupActivity.class);
        startActivity(i);
        this.finish();
    }

}
