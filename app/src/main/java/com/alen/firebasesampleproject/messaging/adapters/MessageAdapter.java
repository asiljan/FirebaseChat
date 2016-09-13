package com.alen.firebasesampleproject.messaging.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alen.firebasesampleproject.R;
import com.alen.firebasesampleproject.common.enums.MessageViewType;
import com.alen.firebasesampleproject.data.models.Message;
import com.alen.firebasesampleproject.messaging.viewholders.BaseViewHolder;
import com.alen.firebasesampleproject.messaging.viewholders.MessageOwnerViewHolder;
import com.alen.firebasesampleproject.messaging.viewholders.MessageViewHolder;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This class consists methods for updating and showing messages.
 *
 * @author Alen Siljan <alen.siljan@gmail.com>
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MessageWrapper> messageWrapperList;
    private Context context;
    private RequestManager rmGlide;

    public MessageAdapter(Context context, RequestManager rmGlide) {
        this.context = context;
        this.rmGlide = rmGlide;
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
                BaseViewHolder messageViewHolder = (MessageViewHolder) holder;
                messageViewHolder.bindData(messageWrapperList.get(position).message, context, rmGlide);
                break;
            case MESSAGE_OWNER:
                BaseViewHolder messageOwnerViewHolder = (MessageOwnerViewHolder) holder;
                messageOwnerViewHolder.bindData(messageWrapperList.get(position).message, context, rmGlide);
                break;
        }
    }

    /**
     * This method populates messages list with new messages and calls notify adapter method.
     *
     * @param messages List<Message>
     * @param ownerUid String user unique ID
     */
    public void updateMessageList(List<Message> messages, String ownerUid) {
        messageWrapperList.clear();
        if (messages.size() > 0) {
            for (Message m : messages) {
                messageWrapperList.add(new MessageWrapper(m, ownerUid.equals(m.getUid())));
            }
            notifyItemInserted(getItemCount());
        } else {
            notifyDataSetChanged();
        }

    }

    /**
     * This method returns MessageViewType enumeration constant as integer.
     *
     * @param position int
     * @return int MessageViewType as integer
     */
    @Override
    public int getItemViewType(int position) {
        return messageWrapperList.get(position).messageViewType.ordinal();
    }

    /**
     * The method returns size of messages list
     *
     * @return int item count
     */
    @Override
    public int getItemCount() {
        if (messageWrapperList != null) {
            return messageWrapperList.size();
        }

        return 0;
    }

    /**
     * This method returns last sent Message from messages list.
     *
     * @return Message model
     */
    public Message getLastSentMessage() {
        return messageWrapperList.get(getItemCount() - 1).message;
    }

    /**
     * This class is used as Wrapper class for Message.
     */
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
