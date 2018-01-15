package com.vibecampo.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Anthony Ngure on 27/10/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class Community implements Parcelable {


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
    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String username;
    @SerializedName("verified")
    private int verified;
    @SerializedName("actual_cover_url")
    private String actualCoverUrl;
    @SerializedName("is_member")
    private boolean isMember;

    public Community() {
    }

    public Community(Parcel in) {
        id = in.readInt();
        addPrivacy = in.readString();
        groupPrivacy = in.readString();
        timelinePostPrivacy = in.readString();
        about = in.readString();
        name = in.readString();
        username = in.readString();
        verified = in.readInt();
        actualCoverUrl = in.readString();
        isMember = in.readByte() != 0;
    }

    public static final Creator<Community> CREATOR = new Creator<Community>() {
        @Override
        public Community createFromParcel(Parcel in) {
            return new Community(in);
        }

        @Override
        public Community[] newArray(int size) {
            return new Community[size];
        }
    };

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

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public int getVerified() {
        return verified;
    }

    public String getActualCoverUrl() {
        return actualCoverUrl;
    }

    public boolean getIsMember() {
        return isMember;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(addPrivacy);
        parcel.writeString(groupPrivacy);
        parcel.writeString(timelinePostPrivacy);
        parcel.writeString(about);
        parcel.writeString(name);
        parcel.writeString(username);
        parcel.writeInt(verified);
        parcel.writeString(actualCoverUrl);
        parcel.writeByte((byte) (isMember ? 1 : 0));
    }
}
