package com.dev.nick.scorch.games;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nick on 10/13/2015.
 */
public class GameBean implements Parcelable {

    public int type = 0;

    public long id = 0;

    public long teamOneId = 0;
    public long teamTwoId = 0;

    public String teamOne = "";
    public String teamTwo = "";

    public int teamOneScore = 0;
    public int teamTwoScore = 0;

    public GameBean(){}

    protected GameBean(Parcel in) {
        type = in.readInt();
        id = in.readLong();
        teamOneId = in.readLong();
        teamTwoId = in.readLong();
        teamOne = in.readString();
        teamTwo = in.readString();
        teamOneScore = in.readInt();
        teamTwoScore = in.readInt();
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeLong(id);
        dest.writeLong(teamOneId);
        dest.writeLong(teamTwoId);
        dest.writeString(teamOne);
        dest.writeString(teamTwo);
        dest.writeInt(teamOneScore);
        dest.writeInt(teamTwoScore);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GameBean> CREATOR = new Parcelable.Creator<GameBean>() {
        @Override
        public GameBean createFromParcel(Parcel in) {
            return new GameBean(in);
        }

        @Override
        public GameBean[] newArray(int size) {
            return new GameBean[size];
        }
    };
}
