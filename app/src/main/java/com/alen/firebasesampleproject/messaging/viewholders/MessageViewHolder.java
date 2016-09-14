package com.alen.firebasesampleproject.messaging.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.alen.firebasesampleproject.R;
import com.alen.firebasesampleproject.common.EventBus;
import com.alen.firebasesampleproject.data.events.UserAccountInfoEvent;
import com.alen.firebasesampleproject.data.models.Message;
import com.bumptech.glide.RequestManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alensiljan on 30/08/16.
 */
public class MessageViewHolder extends BaseViewHolder {

    @BindView(R.id.messengerText)
    TextView mMessageTextView;
    @BindView(R.id.messengerName)
    TextView mMessageNameTextView;
    @BindView(R.id.messengerImageView)
    CircleImageView mCircleImageView;

    public MessageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(final Message message, final Context context, RequestManager glide) {
        mContext = context;
        mMessageTextView.setText(message.getText());
        mMessageNameTextView.setText(buildTimeMessage(message.getName(), message.getmCreatedAt()));

        if (message.getPhotoUrl() == null) {
            mCircleImageView
                    .setImageDrawable(ContextCompat
                            .getDrawable(context,
                                    R.drawable.ic_account_circle_black_36dp));
        } else {
            glide
                    .load(message.getPhotoUrl())
                    .into(mCircleImageView);
        }

        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefaultInstance().post(new UserAccountInfoEvent(message));
                //TODO notify MessageFragment and show UserChatInfoProfile fragment
            }
        });
    }
}
