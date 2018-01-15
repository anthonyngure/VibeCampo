package com.vibecampo.android.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Anthony Ngure on 24/11/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class Comment {


    @SerializedName("id")
    private int id;
    @SerializedName("active")
    private int active;
    @SerializedName("vibe_id")
    private int vibeId;
    @SerializedName("text")
    private String text;
    @SerializedName("time")
    private int time;
    @SerializedName("timeline_id")
    private int timelineId;
    @SerializedName("publisher")
    private Publisher publisher;
    @SerializedName("admin")
    private boolean admin;
    @SerializedName("engagements")
    private Engagements engagements;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getVibeId() {
        return vibeId;
    }

    public void setVibeId(int vibeId) {
        this.vibeId = vibeId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(int timelineId) {
        this.timelineId = timelineId;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Engagements getEngagements() {
        return engagements;
    }

    public void setEngagements(Engagements engagements) {
        this.engagements = engagements;
    }

    public static class Publisher {
        @SerializedName("userId")
        private int userid;
        @SerializedName("userName")
        private String username;
        @SerializedName("userAvatar")
        private String useravatar;
        @SerializedName("fullName")
        private String fullname;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUseravatar() {
            return useravatar;
        }

        public void setUseravatar(String useravatar) {
            this.useravatar = useravatar;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }
    }

    public static class Engagements {
        @SerializedName("likes")
        private int likes;

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }
    }
}
