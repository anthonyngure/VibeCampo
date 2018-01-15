package com.vibecampo.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Anthony Ngure on 27/11/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class Goal implements Parcelable {


    @SerializedName("id")
    private int id;
    @SerializedName("created_at")
    private int createdAt;
    @SerializedName("goal_name")
    private String goalName;
    @SerializedName("goal_description")
    private String goalDescription;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("username")
    private String username;
    @SerializedName("community_id")
    private int communityId;
    @SerializedName("community_name")
    private String communityName;
    @SerializedName("my_goal")
    private boolean myGoal;

    public int getId() {
        return id;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public String getGoalName() {
        return goalName;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public int getCommunityId() {
        return communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public boolean getMyGoal() {
        return myGoal;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.createdAt);
        dest.writeString(this.goalName);
        dest.writeString(this.goalDescription);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.username);
        dest.writeInt(this.communityId);
        dest.writeString(this.communityName);
        dest.writeByte(this.myGoal ? (byte) 1 : (byte) 0);
    }

    public Goal() {
    }

    protected Goal(Parcel in) {
        this.id = in.readInt();
        this.createdAt = in.readInt();
        this.goalName = in.readString();
        this.goalDescription = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.username = in.readString();
        this.communityId = in.readInt();
        this.communityName = in.readString();
        this.myGoal = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Goal> CREATOR = new Parcelable.Creator<Goal>() {
        @Override
        public Goal createFromParcel(Parcel source) {
            return new Goal(source);
        }

        @Override
        public Goal[] newArray(int size) {
            return new Goal[size];
        }
    };

    
}
