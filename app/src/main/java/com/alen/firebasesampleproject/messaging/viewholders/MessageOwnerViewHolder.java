package com.alen.firebasesampleproject.messaging.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.alen.firebasesampleproject.R;
import com.alen.firebasesampleproject.common.util.TimeHelper;
import com.alen.firebasesampleproject.data.models.Message;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alensiljan on 02/09/16.
 */
public class MessageOwnerViewHolder extends BaseViewHolder {

    @BindView(R.id.messengerOwnerText)
    TextView messageText;
    @BindView(R.id.messengerOwnerTime)
    TextView messageDeliverTime;
    @BindView(R.id.messengerOwnerImageView)
    CircleImageView circleImageView;

    public MessageOwnerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Message message, Context context, RequestManager glide) {
        //implement own logic for binding
        mContext = context;
        messageText.setText(message.getText());
        messageDeliverTime.setText(buildTimeMessage(message.getName(), message.getCreatedAt()));

        if (message.getPhotoUrl() == null) {
            circleImageView
                    .setImageDrawable(ContextCompat
                            .getDrawable(context,
                                    R.drawable.ic_account_circle_black_36dp));
        } else {
            glide
                    .load(message.getPhotoUrl())
                    .into(circleImageView);
        }
    }
}
