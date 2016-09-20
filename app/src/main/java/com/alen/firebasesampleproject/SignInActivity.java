package com.alen.firebasesampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.alen.firebasesampleproject.common.BaseActivity;
import com.alen.firebasesampleproject.common.helpers.LogHelper;
import com.alen.firebasesampleproject.di.components.AppComponent;
import com.alen.firebasesampleproject.di.components.LoginComponent;
import com.alen.firebasesampleproject.di.modules.LoginModule;
import com.alen.firebasesampleproject.mvp.presenters.ILoginPresenter;
import com.alen.firebasesampleproject.mvp.views.ILoginView;
import com.alen.firebasesampleproject.navigation.MainActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This class handles user sign in with Google account.
 *
 * @author Alen Siljan <alen.siljan@gmail.com>
 */
public class SignInActivity extends BaseActivity implements View.OnClickListener,
        ILoginView, GoogleApiClient.OnConnectionFailedListener {

    public static final int RC_SIGN_IN = 9001;

    @BindView(R.id.sign_in_button)
    SignInButton mSignInButton;

    @Inject
    protected ILoginPresenter mLoginPresenter;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        mSignInButton.setOnClickListener(this);

        configureGoogleSignIn();
    }

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.plus(new LoginModule(this)).inject(this);
    }

    /**
     * This method creates GoogleSignInOptions object and GoogleApiClient object.
     */
    private void configureGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                mLoginPresenter.signIn(mGoogleApiClient, SignInActivity.this);
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, getString(R.string.google_play_service_error), Toast.LENGTH_SHORT).show();
    }

    /**
     * Method checks for appropriate requestCode and check if SignInResults is success.
     *
     * @param requestCode int
     * @param resultCode  int
     * @param data        Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginPresenter.firebaseAuth(data, requestCode, SignInActivity.this);
    }

    @Override
    public void setAuthFailed() {
        Toast.makeText(SignInActivity.this, getString(R.string.authentication_failed_text),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setGoogleSignInFailed() {
// Google Sign In failed
        LogHelper.printLogMsg("Google Sign In failed.");
    }

    @Override
    public void navigateToMain() {
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }
}
