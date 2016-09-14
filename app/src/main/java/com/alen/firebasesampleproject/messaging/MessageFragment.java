package com.alen.firebasesampleproject.messaging;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.alen.firebasesampleproject.R;
import com.alen.firebasesampleproject.common.Application;
import com.alen.firebasesampleproject.common.EventBus;
import com.alen.firebasesampleproject.common.helpers.FCMHelper;
import com.alen.firebasesampleproject.common.prefs.FirebasePreferences;
import com.alen.firebasesampleproject.data.events.UserAccountInfoEvent;
import com.alen.firebasesampleproject.data.models.Message;
import com.alen.firebasesampleproject.data.models.UserModel;
import com.alen.firebasesampleproject.data.models.UserProfile;
import com.alen.firebasesampleproject.messaging.adapters.MessageAdapter;
import com.alen.firebasesampleproject.messaging.interfaces.MessageInterface;
import com.alen.firebasesampleproject.messaging.views.NewMessageButtonView;
import com.alen.firebasesampleproject.userprofiledetails.UserAccountDetailsFragment;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Operates with sending messages and shows chat
 * messages.
 *
 * @author Alen Siljan <alen.siljan@gmail.com>
 */
public class MessageFragment extends Fragment {

    public static final String TAG = "message_fragment";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 50;
    public static final String MESSAGES_CHILD = "messages";

    @BindView(R.id.btn_send)
    ImageButton mSendButton;
    @BindView(R.id.et_message)
    EditText messageBox;
    @BindView(R.id.messageRecycleList)
    RecyclerView mRecyclerView;
    @BindView(R.id.newMessageBtn)
    NewMessageButtonView newMessageButtonView;

    private LinearLayoutManager mLayoutManager;
    private DatabaseReference mDatabaseReference;
    private UserProfile mUserProfile;
    private UserModel mUserModel;
    private MessageInterface mMessageCallback;
    private MessageAdapter mMessageAdapter;

    @Inject
    Application mApplication;
    @Inject
    SharedPreferences mSharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Application.getInstance().getmAppComponent().inject(this);

        mUserProfile = mApplication.getmUserProfile();
        mUserModel = mApplication.getmUserModel();

        initUI();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefaultInstance().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefaultInstance().unregister(this);
        super.onStop();
    }

    /**
     * Tries to get reference to MessageInterface.
     * Throws ClassCastException otherwise.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mMessageCallback = (MessageInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement MessageInterface");
        }
    }

    /**
     * This method initializes layout components, sets OnClickListener, OnScrollListener
     */
    private void initUI() {
        messageBox.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mSharedPreferences
                .getInt(FirebasePreferences.FRIENDLY_MSG_LENGTH, DEFAULT_MSG_LENGTH_LIMIT))});

        //init recyclerView
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setStackFromEnd(true);

        initFirebaseDB();

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageBox.getText().toString().trim().length() > 0) {
                    Message message = new Message(messageBox.getText().toString(),
                            mUserProfile.getmUserName(), mUserProfile.getmPhotoUrl(), System.currentTimeMillis(),
                            mUserModel.getUser());
                    mDatabaseReference.child(MESSAGES_CHILD).push().setValue(message);

                    FCMHelper.sendNotificationToUser(mUserModel.getUser(), mUserProfile.getmUserName(),
                            messageBox.getText().toString());

                    messageBox.setText("");
                }
            }
        });

        newMessageButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(mMessageAdapter.getItemCount() - 1);
                toggleNewMessageButtonVisibility(false);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mMessageAdapter.getItemCount() - 1 &&
                        isNewMessageButtonVisible()) {
                    toggleNewMessageButtonVisibility(false);
                }
            }
        });
    }

    /**
     * This method creates MessageAdapter object, fetches Firebase Database instance and
     * handles scrolling to position if its necessary.
     */
    private void initFirebaseDB() {
        mMessageAdapter = new MessageAdapter(getActivity(), Glide.with(getActivity()));

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child(MESSAGES_CHILD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Message> snapshotData = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Message message = ds.getValue(Message.class);
                    snapshotData.add(message);
                }

                mMessageCallback.onFirebaseDBDataReady();
                mMessageAdapter.updateMessageList(snapshotData, mUserModel.getUser());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMessageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                int friendlyMessageCount = mMessageAdapter.getItemCount();

                if (friendlyMessageCount > 0) {
                    int lastVisiblePosition =
                            mLayoutManager.findLastCompletelyVisibleItemPosition();
                    if (lastVisiblePosition == -1 ||
                            (positionStart >= (friendlyMessageCount - 1) &&
                                    lastVisiblePosition + 1 == (positionStart - 1))) {
                        mRecyclerView.getLayoutManager().smoothScrollToPosition(mRecyclerView, null, positionStart);
                    } else if (mUserProfile.getmUserName().equals(mMessageAdapter.getLastSentMessage().getName())) {
                        mRecyclerView.smoothScrollToPosition(friendlyMessageCount - 1);
                    } else {
                        //show new messageButton
                        toggleNewMessageButtonVisibility(true);
                    }
                }
            }
        });

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMessageAdapter);
    }

    /**
     * This method checks if 'NewMessage' button is shown
     *
     * @return boolean true if button is shown, false otherwise
     */
    private boolean isNewMessageButtonVisible() {
        return newMessageButtonView.isShown();
    }

    /**
     * This method toggles button 'NewMessage' button visibility
     *
     * @param show boolean true if button needs to be shown, false otherwise
     */
    private void toggleNewMessageButtonVisibility(boolean show) {
        if (show) {
            newMessageButtonView.setVisibility(View.VISIBLE);
        } else {
            newMessageButtonView.setVisibility(View.GONE);
        }
    }

    /**
     * This method handles ClickEvents on Users chat icon and calls
     * method for showing FragmentDialog with User account info
     *
     * @param accountInfoEvent UserAccountInfoEvent object
     */
    @Subscribe
    public void onUserAccountInfoEvent(UserAccountInfoEvent accountInfoEvent) {
        showUserAccountDetailsDialog(accountInfoEvent.getMessage());
    }

    /**
     * This method displays FragmentDialog with User account info details
     *
     * @param message Message object
     */
    private void showUserAccountDetailsDialog(Message message) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(UserAccountDetailsFragment.TAG);
        if (prev != null) {
            ft.remove(prev);
        }

        ft.addToBackStack(null);
        UserAccountDetailsFragment accountDetailsFragment = UserAccountDetailsFragment.newInstance(message);
        accountDetailsFragment.show(ft, UserAccountDetailsFragment.TAG);
    }
}
