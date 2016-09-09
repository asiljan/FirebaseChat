package com.alen.firebasesampleproject.messaging.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alen.firebasesampleproject.common.util.TimeHelper;
import com.alen.firebasesampleproject.data.models.Message;

/**
 * Created by alensiljan on 02/09/16.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    protected Context mContext;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindData(Message message, Context context);

    protected String buildTimeMessage(String name, long time) {
        StringBuilder strBuilder = new StringBuilder(name);
        strBuilder.append(",").append(" ").append(TimeHelper.getMessageSentTime(time, mContext));
        return strBuilder.toString();
    }
}
