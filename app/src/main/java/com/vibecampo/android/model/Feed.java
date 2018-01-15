package com.vibecampo.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthony Ngure on 28/10/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class Feed implements Parcelable {


    @SerializedName("id")
    private int id;
    @SerializedName("activity_text")
    private String activityText;
    @SerializedName("recipient_id")
    private int recipientId;
    @SerializedName("soundcloud_uri")
    private String soundcloudUri;
    @SerializedName("title")
    private String title;
    @SerializedName("text")
    private String text;
    @SerializedName("time")
    private int time;
    @SerializedName("timeline_id")
    private int timelineId;
    @SerializedName("timestamp")
    private String timestamp;
    @SerializedName("category")
    private String category;
    @SerializedName("poll_id")
    private int pollId;
    @SerializedName("link_id")
    private int linkId;
    @SerializedName("to_community")
    private int toCommunity;
    @SerializedName("story_type")
    private String storyType;
    @SerializedName("goal_id")
    private int goalId;
    @SerializedName("goal_activity_text")
    private String goalActivityText;
    @SerializedName("publisher")
    private Publisher publisher;
    @SerializedName("recipient_exists")
    private boolean recipientExists;
    @SerializedName("recipient")
    private Recipient recipient;
    @SerializedName("admin")
    private boolean admin;
    @SerializedName("media_exists")
    private boolean mediaExists;
    @SerializedName("media_type")
    private String mediaType;
    @SerializedName("media_num")
    private int mediaNum;
    @SerializedName("media")
    private List<Media> media;
    @SerializedName("location_exists")
    private boolean locationExists;
    @SerializedName("via_type")
    private String viaType;
    @SerializedName("view_all_comments")
    private boolean viewAllComments;
    @SerializedName("link")
    private List<Link> link;
    @SerializedName("engagements")
    private Engagements engagements;
    @SerializedName("user_liked")
    private boolean userLiked;

    public int getId() {
        return id;
    }

    public String getActivityText() {
        return activityText;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public String getSoundcloudUri() {
        return soundcloudUri;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getTime() {
        return time;
    }

    public int getTimelineId() {
        return timelineId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getCategory() {
        return category;
    }

    public int getPollId() {
        return pollId;
    }

    public int getLinkId() {
        return linkId;
    }

    public int getToCommunity() {
        return toCommunity;
    }

    public String getStoryType() {
        return storyType;
    }

    public int getGoalId() {
        return goalId;
    }

    public String getGoalActivityText() {
        return goalActivityText;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public boolean getRecipientExists() {
        return recipientExists;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public boolean getAdmin() {
        return admin;
    }

    public boolean getMediaExists() {
        return mediaExists;
    }

    public String getMediaType() {
        return mediaType;
    }

    public int getMediaNum() {
        return mediaNum;
    }

    public List<Media> getMedia() {
        return media;
    }

    public boolean getLocationExists() {
        return locationExists;
    }

    public String getViaType() {
        return viaType;
    }

    public boolean getViewAllComments() {
        return viewAllComments;
    }

    public List<Link> getLink() {
        return link;
    }

    public Engagements getEngagements() {
        return engagements;
    }

    public boolean getUserLiked() {
        return userLiked;
    }

    public static class Publisher implements Parcelable {
        @SerializedName("user_id")
        private int userId;
        @SerializedName("username")
        private String username;
        @SerializedName("avatar")
        private String avatar;
        @SerializedName("full_name")
        private String fullName;

        public int getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getFullName() {
            return fullName;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.userId);
            dest.writeString(this.username);
            dest.writeString(this.avatar);
            dest.writeString(this.fullName);
        }

        public Publisher() {
        }

        protected Publisher(Parcel in) {
            this.userId = in.readInt();
            this.username = in.readString();
            this.avatar = in.readString();
            this.fullName = in.readString();
        }

        public static final Creator<Publisher> CREATOR = new Creator<Publisher>() {
            @Override
            public Publisher createFromParcel(Parcel source) {
                return new Publisher(source);
            }

            @Override
            public Publisher[] newArray(int size) {
                return new Publisher[size];
            }
        };
    }

    public static class Recipient implements Parcelable {
        @SerializedName("id")
        private int id;
        @SerializedName("add_privacy")
        private String addPrivacy;
        @SerializedName("group_privacy")
        private String groupPrivacy;
        @SerializedName("timeline_post_privacy")
        private String timelinePostPrivacy;
        @SerializedName("about")
        private String about;
        @SerializedName("active")
        private int active;
        @SerializedName("name")
        private String name;
        @SerializedName("username")
        private String username;

        public int getId() {
            return id;
        }

        public String getAddPrivacy() {
            return addPrivacy;
        }

        public String getGroupPrivacy() {
            return groupPrivacy;
        }

        public String getTimelinePostPrivacy() {
            return timelinePostPrivacy;
        }

        public String getAbout() {
            return about;
        }

        public int getActive() {
            return active;
        }

        public String getName() {
            return name;
        }

        public String getUsername() {
            return username;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.addPrivacy);
            dest.writeString(this.groupPrivacy);
            dest.writeString(this.timelinePostPrivacy);
            dest.writeString(this.about);
            dest.writeInt(this.active);
            dest.writeString(this.name);
            dest.writeString(this.username);
        }

        public Recipient() {
        }

        protected Recipient(Parcel in) {
            this.id = in.readInt();
            this.addPrivacy = in.readString();
            this.groupPrivacy = in.readString();
            this.timelinePostPrivacy = in.readString();
            this.about = in.readString();
            this.active = in.readInt();
            this.name = in.readString();
            this.username = in.readString();
        }

        public static final Creator<Recipient> CREATOR = new Creator<Recipient>() {
            @Override
            public Recipient createFromParcel(Parcel source) {
                return new Recipient(source);
            }

            @Override
            public Recipient[] newArray(int size) {
                return new Recipient[size];
            }
        };
    }

    public static class Media implements Parcelable {
        @SerializedName("id")
        private int id;
        @SerializedName("url")
        private String url;
        @SerializedName("post_id")
        private int postId;
        @SerializedName("post_url")
        private String postUrl;

        public int getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        public int getPostId() {
            return postId;
        }

        public String getPostUrl() {
            return postUrl;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.url);
            dest.writeInt(this.postId);
            dest.writeString(this.postUrl);
        }

        public Media() {
        }

        protected Media(Parcel in) {
            this.id = in.readInt();
            this.url = in.readString();
            this.postId = in.readInt();
            this.postUrl = in.readString();
        }

        public static final Creator<Media> CREATOR = new Creator<Media>() {
            @Override
            public Media createFromParcel(Parcel source) {
                return new Media(source);
            }

            @Override
            public Media[] newArray(int size) {
                return new Media[size];
            }
        };
    }

    public static class Link {
    }

    public static class Engagements implements Parcelable {
        @SerializedName("comments")
        private int comments;
        @SerializedName("likes")
        private int likes;

        public int getComments() {
            return comments;
        }

        public int getLikes() {
            return likes;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.comments);
            dest.writeInt(this.likes);
        }

        public Engagements() {
        }

        protected Engagements(Parcel in) {
            this.comments = in.readInt();
            this.likes = in.readInt();
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public static final Creator<Engagements> CREATOR = new Creator<Engagements>() {
            @Override
            public Engagements createFromParcel(Parcel source) {
                return new Engagements(source);
            }

            @Override
            public Engagements[] newArray(int size) {
                return new Engagements[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.activityText);
        dest.writeInt(this.recipientId);
        dest.writeString(this.soundcloudUri);
        dest.writeString(this.title);
        dest.writeString(this.text);
        dest.writeInt(this.time);
        dest.writeInt(this.timelineId);
        dest.writeString(this.timestamp);
        dest.writeString(this.category);
        dest.writeInt(this.pollId);
        dest.writeInt(this.linkId);
        dest.writeInt(this.toCommunity);
        dest.writeString(this.storyType);
        dest.writeInt(this.goalId);
        dest.writeString(this.goalActivityText);
        dest.writeParcelable(this.publisher, flags);
        dest.writeByte(this.recipientExists ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.recipient, flags);
        dest.writeByte(this.admin ? (byte) 1 : (byte) 0);
        dest.writeByte(this.mediaExists ? (byte) 1 : (byte) 0);
        dest.writeString(this.mediaType);
        dest.writeInt(this.mediaNum);
        dest.writeList(this.media);
        dest.writeByte(this.locationExists ? (byte) 1 : (byte) 0);
        dest.writeString(this.viaType);
        dest.writeByte(this.viewAllComments ? (byte) 1 : (byte) 0);
        dest.writeList(this.link);
        dest.writeParcelable(this.engagements, flags);
        dest.writeByte(this.userLiked ? (byte) 1 : (byte) 0);
    }

    public Feed() {
    }


    public void setUserLiked(boolean userLiked) {
        this.userLiked = userLiked;
    }

    protected Feed(Parcel in) {
        this.id = in.readInt();
        this.activityText = in.readString();
        this.recipientId = in.readInt();
        this.soundcloudUri = in.readString();
        this.title = in.readString();
        this.text = in.readString();
        this.time = in.readInt();
        this.timelineId = in.readInt();
        this.timestamp = in.readString();
        this.category = in.readString();
        this.pollId = in.readInt();
        this.linkId = in.readInt();
        this.toCommunity = in.readInt();
        this.storyType = in.readString();
        this.goalId = in.readInt();
        this.goalActivityText = in.readString();
        this.publisher = in.readParcelable(Publisher.class.getClassLoader());
        this.recipientExists = in.readByte() != 0;
        this.recipient = in.readParcelable(Recipient.class.getClassLoader());
        this.admin = in.readByte() != 0;
        this.mediaExists = in.readByte() != 0;
        this.mediaType = in.readString();
        this.mediaNum = in.readInt();
        this.media = new ArrayList<Media>();
        in.readList(this.media, Media.class.getClassLoader());
        this.locationExists = in.readByte() != 0;
        this.viaType = in.readString();
        this.viewAllComments = in.readByte() != 0;
        this.link = new ArrayList<Link>();
        in.readList(this.link, Link.class.getClassLoader());
        this.engagements = in.readParcelable(Engagements.class.getClassLoader());
        this.userLiked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Feed> CREATOR = new Parcelable.Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel source) {
            return new Feed(source);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}
