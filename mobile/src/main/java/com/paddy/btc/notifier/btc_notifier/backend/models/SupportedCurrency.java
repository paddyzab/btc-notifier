package com.paddy.btc.notifier.btc_notifier.backend.models;

public class SupportedCurrency {
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
}
