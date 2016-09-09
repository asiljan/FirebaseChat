package com.alen.firebasesampleproject.userprofiledetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alen.firebasesampleproject.R;
import com.alen.firebasesampleproject.data.models.Message;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alensiljan on 09/09/16.
 */
public class UserAccountDetailsFragment extends DialogFragment {

    public static final String TAG = "user_account_dialog";
    private static final String INSTANCE_KEY = "message";

    @BindView(R.id.userAccountImage)
    CircleImageView userAccountImage;
    @BindView(R.id.userAccountUsername)
    TextView username;

    private Message message;

    public static UserAccountDetailsFragment newInstance(Message message) {
        Bundle args = new Bundle();

        args.putString(INSTANCE_KEY, new Gson().toJson(message));
        UserAccountDetailsFragment fragment = new UserAccountDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        message = new Gson().fromJson(getArguments().getString(INSTANCE_KEY), Message.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_account, container, false);
        ButterKnife.bind(this, view);

        Glide.with(this)
                .load(message.getPhotoUrl())
                .error(R.drawable.ic_account_circle_black_big)
                .into(userAccountImage);

        username.setText(message.getName());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
