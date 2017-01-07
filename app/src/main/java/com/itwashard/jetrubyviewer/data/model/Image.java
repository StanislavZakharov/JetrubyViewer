package com.itwashard.jetrubyviewer.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by stanislavzakharov on 22.12.16.
 */

public class Image implements Parcelable {
    public String hidpi;
    public String normal;
    public String teaser;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hidpi);
        dest.writeString(this.normal);
        dest.writeString(this.teaser);
    }

    public Image() {
    }

    public String getImage() {
        return hidpi != null ? hidpi : normal;
    }

    protected Image(Parcel in) {
        this.hidpi = in.readString();
        this.normal = in.readString();
        this.teaser = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
