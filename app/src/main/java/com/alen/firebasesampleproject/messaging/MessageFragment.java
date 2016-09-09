package com.alen.firebasesampleproject.messaging;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.alen.firebasesampleproject.common.helpers.FCMHelper;
import com.alen.firebasesampleproject.common.prefs.FirebasePreferences;
import com.alen.firebasesampleproject.data.models.Message;
import com.alen.firebasesampleproject.data.models.UserModel;
import com.alen.firebasesampleproject.data.models.UserProfile;
import com.alen.firebasesampleproject.messaging.adapters.MyFirebaseMessageAdapter;
import com.alen.firebasesampleproject.messaging.interfaces.MessageBehavior;
import com.alen.firebasesampleproject.messaging.views.NewMessageButtonView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alensiljan on 01/09/16.
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

    private LinearLayoutManager llManager;
    private DatabaseReference mDatabaseReference;
    private UserProfile userProfile;
    private UserModel userModel;
    private MessageBehavior messageBehaviorCallback;
    private MyFirebaseMessageAdapter messageAdapter;

    @Inject
    Application application;
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

        ((Application) getActivity().getApplication()).getAppComponent().inject(this);

        userProfile = application.getUserProfile();
        userModel = application.getUserModel();

        initUI();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            messageBehaviorCallback = (MessageBehavior) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement MessageBehavior interface");
        }
    }

    private void initUI() {
        messageBox.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mSharedPreferences
                .getInt(FirebasePreferences.FRIENDLY_MSG_LENGTH, DEFAULT_MSG_LENGTH_LIMIT))});

        //init recyclerView
        llManager = new LinearLayoutManager(getContext());
        llManager.setStackFromEnd(true);

        initFirebaseDB();

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageBox.getText().toString().trim().length() > 0) {
                    Message message = new Message(messageBox.getText().toString(),
                            userProfile.getUserName(), userProfile.getPhotoUrl(), System.currentTimeMillis());
                    mDatabaseReference.child(MESSAGES_CHILD).push().setValue(message);

                    FCMHelper.sendNotificationToUser(userModel.getUser(), userProfile.getUserName(),
                            messageBox.getText().toString());

                    messageBox.setText("");
                }
            }
        });

        newMessageButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                toggleNewMessageButtonVisibility(false);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (llManager.findLastCompletelyVisibleItemPosition() == messageAdapter.getItemCount() - 1 &&
                        isNewMessageButtonVisible()) {
                    toggleNewMessageButtonVisibility(false);
                }
            }
        });
    }

    private void initFirebaseDB() {
        messageAdapter = new MyFirebaseMessageAdapter(getContext());

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child(MESSAGES_CHILD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Message> snapshotData = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Message message = ds.getValue(Message.class);
                    snapshotData.add(message);
                }

                messageBehaviorCallback.onFirebaseDbDataReady();
                messageAdapter.updateMessageList(snapshotData, userProfile.getUserName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        messageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                int friendlyMessageCount = messageAdapter.getItemCount();
                int lastVisiblePosition =
                        llManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition + 1 == (positionStart - 1))) {
                    mRecyclerView.getLayoutManager().smoothScrollToPosition(mRecyclerView, null, positionStart);
                } else {
                    //show new messageButton
                    toggleNewMessageButtonVisibility(true);
                }
            }
        });

        mRecyclerView.setLayoutManager(llManager);
        mRecyclerView.setAdapter(messageAdapter);
    }

    private boolean isNewMessageButtonVisible() {
        return newMessageButtonView.isShown();
    }

    private void toggleNewMessageButtonVisibility(boolean show) {
        if (show) {
            newMessageButtonView.setVisibility(View.VISIBLE);
        } else {
            newMessageButtonView.setVisibility(View.GONE);
        }
    }
}
