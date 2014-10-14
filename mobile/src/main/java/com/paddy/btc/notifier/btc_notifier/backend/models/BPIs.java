package com.paddy.btc.notifier.btc_notifier.backend.models;

import com.google.gson.annotations.SerializedName;

public class BPIs {
    @SerializedName("USD")
    private BPI bpiForUsd;
    @SerializedName("GPB")
    private BPI bpiForGpb;
    @SerializedName("EUR")
    private BPI bpiForEur;

    public BPI getBpiForUsd() {
        return bpiForUsd;
    }

    public void setBpiForUsd(BPI bpiForUsd) {
        this.bpiForUsd = bpiForUsd;
    }

    public BPI getBpiForGpb() {
        return bpiForGpb;
    }

    public void setBpiForGpb(BPI bpiForGpb) {
        this.bpiForGpb = bpiForGpb;
    }

    public BPI getBpiForEur() {
        return bpiForEur;
    }

    public void setBpiForEur(BPI bpiForEur) {
        this.bpiForEur = bpiForEur;
    }
}
