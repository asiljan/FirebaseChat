package com.alen.firebasesampleproject.messaging.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alen.firebasesampleproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alensiljan on 08/09/16.
 */
public class NewMessageButtonView extends LinearLayout {

    @BindView(R.id.new_msg_btn_holder)
    LinearLayout buttonHolder;
    @BindView(R.id.new_msg_btn_text)
    TextView buttonText;

    public NewMessageButtonView(Context context) {
        super(context);
        initButton(context, null);
    }

    public NewMessageButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initButton(context, attrs);
    }

    private void initButton(Context context, AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_new_msg_button, this);
        ButterKnife.bind(this, view);

        if (attrs != null) {
            TypedArray tArray = context.obtainStyledAttributes(attrs, R.styleable.NewMessageButtonView);

            String strBtnText = tArray.getString(R.styleable.NewMessageButtonView_buttonText);

            int bckgColor = tArray.getColor(R.styleable.NewMessageButtonView_buttonBackground,
                    context.getResources().getColor(R.color.colorPrimary));
            int textColor = tArray.getColor(R.styleable.NewMessageButtonView_buttonTextColor,
                    context.getResources().getColor(R.color.white));

            tArray.recycle();

            buttonHolder.setBackgroundColor(bckgColor);
            buttonText.setText(strBtnText);
            buttonText.setTextColor(textColor);
        }

    }
}
