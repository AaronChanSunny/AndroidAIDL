package me.aaronchan.androidaidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aaronchan on 16/5/17.
 */
public class Packet implements Parcelable {
    private int mId;
    private long mLength;
    private String mContent;

    public Packet() {
    }

    public Packet(int id, long length, String content) {
        mId = id;
        mLength = length;
        mContent = content;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public long getLength() {
        return mLength;
    }

    public void setLength(long length) {
        mLength = length;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeLong(this.mLength);
        dest.writeString(this.mContent);
    }

    protected Packet(Parcel in) {
        this.mId = in.readInt();
        this.mLength = in.readLong();
        this.mContent = in.readString();
    }

    public static final Parcelable.Creator<Packet> CREATOR = new Parcelable.Creator<Packet>() {
        @Override
        public Packet createFromParcel(Parcel source) {
            return new Packet(source);
        }

        @Override
        public Packet[] newArray(int size) {
            return new Packet[size];
        }
    };
}
