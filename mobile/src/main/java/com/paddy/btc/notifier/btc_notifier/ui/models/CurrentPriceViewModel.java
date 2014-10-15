package com.paddy.btc.notifier.btc_notifier.ui.models;

public class CurrentPriceViewModel {

    private String updatedAt;
    private String formattedRate;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFormattedRate() {
        return formattedRate;
    }

    public void setFormattedRate(String formattedRate) {
        this.formattedRate = formattedRate;
    }
}
