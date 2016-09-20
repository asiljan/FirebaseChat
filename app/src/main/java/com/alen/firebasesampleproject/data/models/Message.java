package com.alen.firebasesampleproject.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by alensiljan on 30/08/16.
 */
public class Message implements Serializable {

    @SerializedName("createdAt")
    private long mCreatedAt;
    @SerializedName("text")
    private String mText;
    @SerializedName("name")
    private String mName;
    @SerializedName("photoUrl")
    private String mPhotoUrl;
    @SerializedName("uid")
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

    public long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(long mCreatedAt) {
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

    public void setUid(String mUid) {
        this.mUid = mUid;
    }
}
