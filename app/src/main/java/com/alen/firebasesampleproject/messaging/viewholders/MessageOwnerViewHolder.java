package com.alen.firebasesampleproject.messaging.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.alen.firebasesampleproject.R;
import com.alen.firebasesampleproject.data.models.Message;
import com.bumptech.glide.RequestManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alensiljan on 02/09/16.
 */
public class MessageOwnerViewHolder extends BaseViewHolder {

    @BindView(R.id.messengerOwnerText)
    TextView mMessageText;
    @BindView(R.id.messengerOwnerTime)
    TextView mMessageDeliverTime;
    @BindView(R.id.messengerOwnerImageView)
    CircleImageView mCircleImageView;

    public MessageOwnerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Message message, Context context, RequestManager glide) {
        //implement own logic for binding
        mContext = context;
        mMessageText.setText(message.getText());
        mMessageDeliverTime.setText(buildTimeMessage(message.getName(), message.getmCreatedAt()));

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
    }
}
