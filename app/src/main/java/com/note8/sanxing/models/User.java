package com.note8.sanxing.models;

import java.util.ArrayList;

/**
 * Created by BenWwChen on 2017/3/30.
 */

public class User {

    private String username;
    private String avatar;
    private ArrayList<String> tags;

    public User(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
