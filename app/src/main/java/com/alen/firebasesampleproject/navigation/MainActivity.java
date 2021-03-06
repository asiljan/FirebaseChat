package com.alen.firebasesampleproject.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.alen.firebasesampleproject.R;
import com.alen.firebasesampleproject.SignInActivity;
import com.alen.firebasesampleproject.common.Application;
import com.alen.firebasesampleproject.common.BaseActivity;
import com.alen.firebasesampleproject.common.EventBus;
import com.alen.firebasesampleproject.common.RestManager;
import com.alen.firebasesampleproject.common.helpers.FCMHelper;
import com.alen.firebasesampleproject.common.helpers.LogHelper;
import com.alen.firebasesampleproject.data.events.UserCredentialEvent;
import com.alen.firebasesampleproject.data.models.UserModel;
import com.alen.firebasesampleproject.data.models.UserProfile;
import com.alen.firebasesampleproject.di.components.AppComponent;
import com.alen.firebasesampleproject.messaging.MessageFragment;
import com.alen.firebasesampleproject.messaging.interfaces.MessageInterface;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MainActivity class handles with navigation through the application.
 * Checks if user is authenticate user and shows SignIn screen otherwise,
 * Shows MessageFragment, handles invitations.
 *
 * @author Alen Siljan <alen.siljan@gmail.com>
 */
public class MainActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener,
        MessageInterface {

    public static final String ANONYMOUS = "anonymous";
    private static int HOLDER_VIEW_ID = R.id.message_fragment;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    Application mApplication;
    @Inject
    RestManager mRestManager;
    @Inject
    FirebaseAuth mFirebaseAuth;

    private GoogleApiClient mGoogleApiClient;
    private String mPhotoUrl;
    private String mUsername;
    private UserModel mUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mUsername = ANONYMOUS;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .addApi(AppInvite.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefaultInstance().register(this);
        initFirebaseAuth();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendUserCredentials();
    }

    @Override
    protected void onStop() {
        EventBus.getDefaultInstance().unregister(this);
        super.onStop();
    }

    /**
     * The method calls sendUserCredentails method of RestManager class.
     */
    private void sendUserCredentials() {
        mRestManager.sendUserCredentials(mUserModel);
    }

    /**
     * This method checks if user is authenticated.
     */
    private void initFirebaseAuth() {

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
            mUserModel = new UserModel(mFirebaseUser.getUid(),
                    FirebaseInstanceId.getInstance().getToken());
            storeUserProfile();
        }
    }

    /**
     * This method saves UserModel and UserProfile object into Application class.
     */
    private void storeUserProfile() {

        UserProfile userProfile = new UserProfile();
        userProfile.setmUserName(mUsername);
        userProfile.setmPhotoUrl(mPhotoUrl);
        mApplication.setmUserProfile(userProfile);
        mApplication.setmUserModel(mUserModel);
        LogHelper.printLogMsg("INFO: " + userProfile.getmUserName() + ", " + mUserModel.getUser());
        initMessageFragment();
    }

    @Subscribe
    public void onUserCredentialEvent(UserCredentialEvent event) {
        if (!event.isSuccess()) {
            LogHelper.printLogMsg(getString(R.string.push_not_available));
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * This method initializes MessageFragment.
     */
    private void initMessageFragment() {
        if (getSupportFragmentManager().findFragmentByTag(MessageFragment.TAG) == null) {
            FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
            MessageFragment messageFragment = new MessageFragment();
            fTransaction.add(HOLDER_VIEW_ID, messageFragment, MessageFragment.TAG);
            fTransaction.commit();
        }
    }

    /**
     * This method is MessageInterface method and it fires up when Firebase database is ready.
     */
    @Override
    public void onFirebaseDBDataReady() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                signOut();
                return true;
            case R.id.send_invitation:
                FCMHelper.sendInvitation(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method handles user sign out process.
     */
    private void signOut() {
        mFirebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        mPhotoUrl = null;
        mUserModel = null;
        mUsername = null;
        mApplication.setmUserModel(null);
        mApplication.setmUserProfile(null);
        startActivity(new Intent(this, SignInActivity.class));
        this.finish();
    }

    /**
     * This method checks if invitation was successfully or not.
     *
     * @param requestCode int requestCode
     * @param resultCode  int resultCode
     * @param data        Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FCMHelper.REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                String[] ids = AppInviteInvitation
                        .getInvitationIds(resultCode, data);
                LogHelper.printLogMsg("Invitation sent: " + ids.length);
            } else {
                LogHelper.printLogMsg("Failed to send invitation");
            }
        }
    }

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.inject(this);
    }
}
