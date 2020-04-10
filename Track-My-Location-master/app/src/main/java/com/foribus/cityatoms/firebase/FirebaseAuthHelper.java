package com.foribus.cityatoms.firebase;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import timber.log.Timber;


public class FirebaseAuthHelper {

    private FirebaseAuth mAuth;

    private Context mContext;

    public FirebaseAuthHelper(Context context) {
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
    }

    public static void doAnonymouslySignOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public void generateFirebaseInstanceId(FirebaseAuthCallback callback) {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            callback.onSuccess(currentUser);
            return;
        }

        mAuth.signInAnonymously()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.d("signInAnonymously:success");
                        callback.onSuccess(task.getResult().getUser());
                    } else {
                        // If sign in fails, display a message to the user.
                        Timber.e(task.getException());
                        callback.onFail();
                    }
                });
    }

    public interface FirebaseAuthCallback {
        void onSuccess(FirebaseUser user);

        void onFail();
    }
}
