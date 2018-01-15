package com.vibecampo.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {


    @SerializedName("appkey")
    private String appkey;
    @SerializedName("user_id")
    private long userId;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("username")
    private String username;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("email")
    private String email;
    @SerializedName("gender")
    private String gender;
    @SerializedName("account_confirmed")
    private int accountConfirmed;
    @SerializedName("privacy")
    private Privacy privacy;

    @SerializedName("user_followings")
    private int userFollowings;
    @SerializedName("user_followers")
    private int userFollowers;
    @SerializedName("user_tips")
    private int userTips;
    @SerializedName("user_notifications")
    private int userNotifications;
    @SerializedName("user_chats")
    private int userChats;

    public int getUserFollowings() {
        return userFollowings;
    }

    public User setUserFollowings(int userFollowings) {
        this.userFollowings = userFollowings;
        return this;
    }

    public int getUserFollowers() {
        return userFollowers;
    }

    public User setUserFollowers(int userFollowers) {
        this.userFollowers = userFollowers;
        return this;
    }

    public int getUserTips() {
        return userTips;
    }

    public User setUserTips(int userTips) {
        this.userTips = userTips;
        return this;
    }

    public int getUserNotifications() {
        return userNotifications;
    }

    public User setUserNotifications(int userNotifications) {
        this.userNotifications = userNotifications;
        return this;
    }

    public int getUserChats() {
        return userChats;
    }

    public User setUserChats(int userChats) {
        this.userChats = userChats;
        return this;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAccountConfirmed() {
        return accountConfirmed;
    }

    public void setAccountConfirmed(int accountConfirmed) {
        this.accountConfirmed = accountConfirmed;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public static class Privacy implements Parcelable {
        @SerializedName("phone_number_privacy")
        private String phoneNumberPrivacy;
        @SerializedName("follow_privacy")
        private String followPrivacy;
        @SerializedName("confirm_followers")
        private String confirmFollowers;
        @SerializedName("messages_privacy")
        private String messagesPrivacy;
        @SerializedName("vibes_privacy")
        private String vibesPrivacy;
        @SerializedName("vibes_comment_privacy")
        private String vibesCommentPrivacy;
        @SerializedName("timeline_vibes_privacy")
        private String timelineVibesPrivacy;



        public String getPhoneNumberPrivacy() {
            return phoneNumberPrivacy;
        }

        public void setPhoneNumberPrivacy(String phoneNumberPrivacy) {
            this.phoneNumberPrivacy = phoneNumberPrivacy;
        }

        public String getFollowPrivacy() {
            return followPrivacy;
        }

        public void setFollowPrivacy(String followPrivacy) {
            this.followPrivacy = followPrivacy;
        }

        public String getConfirmFollowers() {
            return confirmFollowers;
        }

        public void setConfirmFollowers(String confirmFollowers) {
            this.confirmFollowers = confirmFollowers;
        }

        public String getMessagesPrivacy() {
            return messagesPrivacy;
        }

        public void setMessagesPrivacy(String messagesPrivacy) {
            this.messagesPrivacy = messagesPrivacy;
        }

        public String getVibesPrivacy() {
            return vibesPrivacy;
        }

        public void setVibesPrivacy(String vibesPrivacy) {
            this.vibesPrivacy = vibesPrivacy;
        }

        public String getVibesCommentPrivacy() {
            return vibesCommentPrivacy;
        }

        public void setVibesCommentPrivacy(String vibesCommentPrivacy) {
            this.vibesCommentPrivacy = vibesCommentPrivacy;
        }

        public String getTimelineVibesPrivacy() {
            return timelineVibesPrivacy;
        }

        public void setTimelineVibesPrivacy(String timelineVibesPrivacy) {
            this.timelineVibesPrivacy = timelineVibesPrivacy;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.phoneNumberPrivacy);
            dest.writeString(this.followPrivacy);
            dest.writeString(this.confirmFollowers);
            dest.writeString(this.messagesPrivacy);
            dest.writeString(this.vibesPrivacy);
            dest.writeString(this.vibesCommentPrivacy);
            dest.writeString(this.timelineVibesPrivacy);
        }

        public Privacy() {
        }

        protected Privacy(Parcel in) {
            this.phoneNumberPrivacy = in.readString();
            this.followPrivacy = in.readString();
            this.confirmFollowers = in.readString();
            this.messagesPrivacy = in.readString();
            this.vibesPrivacy = in.readString();
            this.vibesCommentPrivacy = in.readString();
            this.timelineVibesPrivacy = in.readString();
        }

        public static final Creator<Privacy> CREATOR = new Creator<Privacy>() {
            @Override
            public Privacy createFromParcel(Parcel source) {
                return new Privacy(source);
            }

            @Override
            public Privacy[] newArray(int size) {
                return new Privacy[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appkey);
        dest.writeLong(this.userId);
        dest.writeString(this.avatar);
        dest.writeString(this.username);
        dest.writeString(this.fullName);
        dest.writeString(this.email);
        dest.writeString(this.gender);
        dest.writeInt(this.accountConfirmed);
        dest.writeParcelable(this.privacy, flags);
        dest.writeInt(this.userFollowings);
        dest.writeInt(this.userFollowers);
        dest.writeInt(this.userTips);
        dest.writeInt(this.userNotifications);
        dest.writeInt(this.userChats);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.appkey = in.readString();
        this.userId = in.readLong();
        this.avatar = in.readString();
        this.username = in.readString();
        this.fullName = in.readString();
        this.email = in.readString();
        this.gender = in.readString();
        this.accountConfirmed = in.readInt();
        this.privacy = in.readParcelable(Privacy.class.getClassLoader());
        this.userFollowings = in.readInt();
        this.userFollowers = in.readInt();
        this.userTips = in.readInt();
        this.userNotifications = in.readInt();
        this.userChats = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
