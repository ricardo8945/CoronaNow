package com.example.coronanow.ui.country;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;

public class CovidCountry implements Parcelable {
    String mCovidCountry, mTodayCases, mDeaths, mTodayDeaths, mRecovered, mCritical, mActive, mFlags;
    Integer mCases;
    public CovidCountry(String mCovidCountry, int mCases, String mTodayCases, String mDeaths, String mTodayDeaths, String mRecovered, String mCritical, String mActive, String mFlags) {
        this.mCovidCountry = mCovidCountry;
        this.mCases = mCases;
        this.mTodayCases = mTodayCases;
        this.mDeaths = mDeaths;
        this.mTodayDeaths = mTodayDeaths;
        this.mRecovered = mRecovered;
        this.mCritical = mCritical;
        this.mActive = mActive;
        this.mFlags = mFlags;
    }
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    public String getmCovidCountry() {
        return mCovidCountry;
    }

    public int getmCases() {
        return mCases;
    }

    public String getmTodayCases() {
        Integer mtc=Integer.valueOf(mTodayCases);
        String MTodayCases= formatter.format(mtc);
        return MTodayCases;
    }

    public String getmDeaths() {
        Integer md=Integer.valueOf(mDeaths);
        String MDeaths= formatter.format(md);
        return MDeaths;
    }

    public String getmTodayDeaths() {
        Integer mtd=Integer.valueOf(mTodayCases);
        String MTodayDeaths= formatter.format(mtd);
        return MTodayDeaths;
    }

    public String getmRecovered() {
        Integer mr=Integer.valueOf(mRecovered);
        String MRecovered= formatter.format(mr);
        return MRecovered;
    }

    public String getmCritical() {
        Integer mtcr=Integer.valueOf(mCritical);
        String MCritical= formatter.format(mtcr);
        return MCritical;
    }

    public String getmActive() {
        Integer ma=Integer.valueOf(mActive);
        String MActive= formatter.format(ma);
        return MActive;
    }

    public String getmFlags() {
        return mFlags;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCovidCountry);
        dest.writeInt(this.mCases);
        dest.writeString(this.mTodayCases);
        dest.writeString(this.mDeaths);
        dest.writeString(this.mTodayDeaths);
        dest.writeString(this.mRecovered);
        dest.writeString(this.mCritical);
        dest.writeString(this.mActive);
        dest.writeString(this.mFlags);
    }

    protected CovidCountry(Parcel in) {
        this.mCovidCountry = in.readString();
        this.mCases = in.readInt();
        this.mTodayCases = in.readString();
        this.mDeaths = in.readString();
        this.mTodayDeaths = in.readString();
        this.mRecovered = in.readString();
        this.mCritical = in.readString();
        this.mActive = in.readString();
        this.mFlags = in.readString();
    }

    public static final Creator<CovidCountry> CREATOR = new Creator<CovidCountry>() {
        @Override
        public CovidCountry createFromParcel(Parcel source) {
            return new CovidCountry(source);
        }

        @Override
        public CovidCountry[] newArray(int size) {
            return new CovidCountry[size];
        }
    };
}
