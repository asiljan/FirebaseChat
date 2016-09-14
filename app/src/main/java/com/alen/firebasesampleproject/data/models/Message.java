package com.alen.firebasesampleproject.data.models;

/**
 * Created by alensiljan on 30/08/16.
 */
public class Message {

    private long mCreatedAt;
    private String mText;
    private String mName;
    private String mPhotoUrl;
    private String mUid;

    public Message() {
    }

    public Message(String mText, String mName, String mPhotoUrl, long mCreatedAt, String mUid) {
        this.mCreatedAt = mCreatedAt;
        this.mText = mText;
        this.mName = mName;
        this.mPhotoUrl = mPhotoUrl;
        this.mUid = mUid;
    }

    public Long getmCreatedAt() {
        return mCreatedAt;
    }

    public void setmCreatedAt(long mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }

    public String getUid() {
        return mUid;
    }
}
