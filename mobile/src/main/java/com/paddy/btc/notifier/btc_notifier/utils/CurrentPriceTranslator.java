package com.paddy.btc.notifier.btc_notifier.utils;

import com.paddy.btc.notifier.btc_notifier.backend.models.BPI;
import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponse;
import com.paddy.btc.notifier.btc_notifier.storage.CurrencyProvider;
import org.apache.commons.lang.StringEscapeUtils;

public class CurrentPriceTranslator {

    private final CurrencyProvider currencyProvider;

    public CurrentPriceTranslator(final CurrencyProvider currencyProvider) {
        this.currencyProvider = currencyProvider;
    }

    public String getFormatted(final GetCurrentPriceResponse getCurrentPriceResponse) {

        final String currentCurrency = currencyProvider.getCurrentCurrency().toUpperCase();
        String formattedString;

        if (getCurrentPriceResponse.getBPIs().containsKey(currentCurrency)) {
            final BPI bpi = getCurrentPriceResponse.getBPIs().get(currentCurrency);
            formattedString = bpi.getRate() + " " + currentCurrency;
        } else {
            // default value for USD
            final BPI bpi = getCurrentPriceResponse.getBPIs().get("USD");
            formattedString = bpi.getRate() + " " + "USD";
        }

        return formattedString;
    }

    private String escapeSymbol(final String symbol) {
        return StringEscapeUtils.unescapeHtml(symbol);
    }
}
