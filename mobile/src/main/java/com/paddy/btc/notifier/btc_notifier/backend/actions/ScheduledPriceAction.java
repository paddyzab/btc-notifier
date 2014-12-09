package com.paddy.btc.notifier.btc_notifier.backend.actions;

import com.paddy.btc.notifier.btc_notifier.backend.api.ICoinbaseAPI;
import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponse;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ScheduledPriceAction implements Action0 {

    private final ICoinbaseAPI coinbaseAPI;
    private final Action1<GetCurrentPriceResponse> updatePrice;
    private final Action1<Throwable> logError;

    public ScheduledPriceAction(final ICoinbaseAPI coinbaseAPI, final Action1<GetCurrentPriceResponse> updatePrice, final Action1<Throwable> logError) {
        this.coinbaseAPI = coinbaseAPI;
        this.updatePrice = updatePrice;
        this.logError = logError;
    }

    @Override
    public void call() {
        coinbaseAPI.getCurrentBpi().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(updatePrice, logError);
    }
}
