package com.alen.firebasesampleproject.mvp.interactors.impl;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.alen.firebasesampleproject.SignInActivity;
import com.alen.firebasesampleproject.mvp.interactors.ILoginInteractor;
import com.alen.firebasesampleproject.mvp.listeners.LoginListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

/**
 * Created by alensiljan on 19/09/16.
 */
public class LoginInteractorImpl implements ILoginInteractor {

    private LoginListener mLoginListener;
    private FirebaseAuth mFirebaseAuth;

    @Inject
    public LoginInteractorImpl(FirebaseAuth mFirebaseAuth) {
        this.mFirebaseAuth = mFirebaseAuth;
    }

    @Override
    public void processSignIn(Intent intent, Activity activity) {
        activity.startActivityForResult(intent, SignInActivity.RC_SIGN_IN);
    }

    @Override
    public void processFirebaseAuth(Intent data, int requestCode, Activity activity, LoginListener loginListener) {
        this.mLoginListener = loginListener;
        if (requestCode == SignInActivity.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuth(account.getIdToken(), activity);
            } else {
                mLoginListener.onGoogleSignInFailed();
            }
        }
    }

    /**
     * The method initiates signIn with fetched credentials and starts MainActivity
     * if Sign In is complete.
     *
     * @param mIdToken  String
     * @param mActivity Activity
     */
    private void firebaseAuth(String mIdToken, Activity mActivity) {
        AuthCredential credential = GoogleAuthProvider.getCredential(mIdToken, null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            mLoginListener.onFirebaseAuthFailed();
                        } else {
                            mLoginListener.onFirebaseAuthSuccess();
                        }
                    }
                });
    }
}
