package com.alen.firebasesampleproject;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alensiljan on 30/08/16.
 */
public class MessageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.messengerText)
    TextView messageTextView;
    @BindView(R.id.messengerName)
    TextView messageNameTextView;
    @BindView(R.id.messengerImageView)
    CircleImageView circleImageView;

    public MessageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void populateData(Message message, Context context) {
        messageTextView.setText(message.getText());
        messageNameTextView.setText(message.getName());
        if (message.getPhotoUrl() == null) {
            circleImageView
                    .setImageDrawable(ContextCompat
                            .getDrawable(context,
                                    R.drawable.ic_account_circle_black_36dp));
        } else {
            Glide.with(context)
                    .load(message.getPhotoUrl())
                    .into(circleImageView);
        }
    }
}
