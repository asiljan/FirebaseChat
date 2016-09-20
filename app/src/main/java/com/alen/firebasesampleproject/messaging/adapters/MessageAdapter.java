package com.alen.firebasesampleproject.messaging.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alen.firebasesampleproject.R;
import com.alen.firebasesampleproject.common.enums.MessageViewType;
import com.alen.firebasesampleproject.common.helpers.LogHelper;
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

    private List<MessageWrapper> mMessageWrapperList;
    private Context mContext;
    private RequestManager mGlide;

    public MessageAdapter(Context mContext, RequestManager mGlide) {
        this.mContext = mContext;
        this.mGlide = mGlide;
        mMessageWrapperList = new ArrayList<>();
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
        MessageWrapper messageWrapper = mMessageWrapperList.get(position);

        switch (messageWrapper.mMessageViewType) {
            case MESSAGE:
                BaseViewHolder messageViewHolder = (MessageViewHolder) holder;
                messageViewHolder.bindData(mMessageWrapperList.get(position).mMessage, mContext, mGlide);
                break;
            case MESSAGE_OWNER:
                BaseViewHolder messageOwnerViewHolder = (MessageOwnerViewHolder) holder;
                messageOwnerViewHolder.bindData(mMessageWrapperList.get(position).mMessage, mContext, mGlide);
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
        mMessageWrapperList.clear();
        if (messages.size() > 0) {
            for (Message m : messages) {
                LogHelper.printLogMsg("INFO? " + ownerUid + ", message Uid: " + m.getUid());
                LogHelper.printLogMsg("INFO equals: " + ownerUid.equals(m.getUid()));
                mMessageWrapperList.add(new MessageWrapper(m, ownerUid.equals(m.getUid())));
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
        return mMessageWrapperList.get(position).mMessageViewType.ordinal();
    }

    /**
     * The method returns size of messages list
     *
     * @return int item count
     */
    @Override
    public int getItemCount() {
        if (mMessageWrapperList != null) {
            return mMessageWrapperList.size();
        }

        return 0;
    }

    /**
     * This method returns last sent Message from messages list.
     *
     * @return Message model
     */
    public Message getLastSentMessage() {
        return mMessageWrapperList.get(getItemCount() - 1).mMessage;
    }

    /**
     * This class is used as Wrapper class for Message.
     */
    class MessageWrapper {
        private MessageViewType mMessageViewType;
        private Message mMessage;

        public MessageWrapper(Message mMessage, boolean ownerMsg) {
            this.mMessage = mMessage;

            if (ownerMsg) {
                mMessageViewType = MessageViewType.MESSAGE_OWNER;
            } else {
                mMessageViewType = MessageViewType.MESSAGE;
            }
        }
    }
}
