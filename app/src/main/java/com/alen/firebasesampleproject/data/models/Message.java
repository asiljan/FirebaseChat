package com.alen.firebasesampleproject.data.models;

/**
 * Created by alensiljan on 30/08/16.
 */
public class Message {

    private long createdAt;
    private String text;
    private String name;
    private String photoUrl;

    public Message() {
    }

    public Message(String text, String name, String photoUrl, long createdAt) {
        this.createdAt = createdAt;
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
