package com.paddy.btc.notifier.btc_notifier.backend.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SupportedCurrency implements Parcelable {
    private String currency;
    private String country;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    protected SupportedCurrency(Parcel in) {
        currency = in.readString();
        country = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currency);
        dest.writeString(country);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SupportedCurrency> CREATOR = new Parcelable.Creator<SupportedCurrency>() {
        @Override
        public SupportedCurrency createFromParcel(Parcel in) {
            return new SupportedCurrency(in);
        }

        @Override
        public SupportedCurrency[] newArray(int size) {
            return new SupportedCurrency[size];
        }
    };
}