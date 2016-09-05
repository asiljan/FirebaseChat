package com.alen.firebasesampleproject.messaging.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alen.firebasesampleproject.data.models.Message;

/**
 * Created by alensiljan on 02/09/16.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void bindData(Message message, Context context);
}