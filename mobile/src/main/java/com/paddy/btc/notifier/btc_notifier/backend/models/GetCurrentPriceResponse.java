package com.paddy.btc.notifier.btc_notifier.backend.models;

import com.google.gson.annotations.SerializedName;

public class GetCurrentPriceResponse {

    private Time time;
    private String disclaimer;
    @SerializedName("bpi")
    private BPIs BPIs;

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public BPIs getBPIs() {
        return BPIs;
    }

    public void setBPIs(BPIs BPIs) {
        this.BPIs = BPIs;
    }
}
