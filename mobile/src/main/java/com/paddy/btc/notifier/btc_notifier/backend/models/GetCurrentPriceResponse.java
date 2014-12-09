package com.paddy.btc.notifier.btc_notifier.backend.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class GetCurrentPriceResponse {

    private Time time;
    private String disclaimer;
    @SerializedName("bpi")
    private Map<String, BPI> BPIs;

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

    public Map<String, BPI>  getBPIs() {
        return BPIs;
    }

    public void setBPIs(Map<String, BPI> BPIs) {
        this.BPIs = BPIs;
    }
}
