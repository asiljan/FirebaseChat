package com.alen.firebasesampleproject.messaging.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alen.firebasesampleproject.R;
import com.alen.firebasesampleproject.common.enums.MessageViewType;
import com.alen.firebasesampleproject.data.models.Message;
import com.alen.firebasesampleproject.messaging.viewholders.MessageOwnerViewHolder;
import com.alen.firebasesampleproject.messaging.viewholders.MessageViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alensiljan on 02/09/16.
 */
public class MyFirebaseMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MessageWrapper> messageWrapperList;
    private Context context;

    public MyFirebaseMessageAdapter(Context context) {
        this.context = context;
        messageWrapperList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (MessageViewType.MESSAGE.ordinal() == viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message, parent, false);
            return new MessageViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_owner, parent, false);
            return new MessageOwnerViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageWrapper messageWrapper = messageWrapperList.get(position);

        switch (messageWrapper.messageViewType) {
            case MESSAGE:
                MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
                messageViewHolder.bindData(messageWrapperList.get(position).message, context);
                break;
            case MESSAGE_OWNER:
                MessageOwnerViewHolder messageOwnerViewHolder = (MessageOwnerViewHolder) holder;
                messageOwnerViewHolder.bindData(messageWrapperList.get(position).message, context);
                break;
        }
    }

    public void updateMessageList(List<Message> messages, String ownerUsername) {
        messageWrapperList.clear();
        for (Message m : messages) {
            messageWrapperList.add(new MessageWrapper(m, ownerUsername.equals(m.getName())));
        }

        notifyItemInserted(getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        return messageWrapperList.get(position).messageViewType.ordinal();
    }

    @Override
    public int getItemCount() {
        if (messageWrapperList != null) {
            return messageWrapperList.size();
        }

        return 0;
    }

    class MessageWrapper {
        private MessageViewType messageViewType;
        private Message message;

        public MessageWrapper(Message message, boolean ownerMsg) {
            this.message = message;

            if (ownerMsg) {
                messageViewType = MessageViewType.MESSAGE_OWNER;
            } else {
                messageViewType = MessageViewType.MESSAGE;
            }
        }
    }
}
