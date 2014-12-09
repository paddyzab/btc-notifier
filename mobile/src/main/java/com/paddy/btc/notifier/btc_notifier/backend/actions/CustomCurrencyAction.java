package com.paddy.btc.notifier.btc_notifier.backend.actions;

import com.paddy.btc.notifier.btc_notifier.backend.api.ICoinbaseAPI;
import com.paddy.btc.notifier.btc_notifier.storage.CurrencyProvider;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class CustomCurrencyAction implements Action0 {

    private final ICoinbaseAPI coinbaseAPI;
    private final UpdatePriceAction updatePrice;
    private final LogError logError;
    private final CurrencyProvider currencyProvider;

    public CustomCurrencyAction(final CurrencyProvider currencyProvider,
                                final ICoinbaseAPI coinbaseAPI,
                                final UpdatePriceAction updatePrice,
                                final LogError logError) {
        this.currencyProvider = currencyProvider;
        this.coinbaseAPI = coinbaseAPI;
        this.updatePrice = updatePrice;
        this.logError = logError;
    }

    @Override
    public void call() {
        coinbaseAPI.getCurrentPriceResponseForISOCode(currencyProvider.getCurrentCurrency()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(updatePrice, logError);
    }
}
