package com.paddy.btc.notifier.btc_notifier.backend.actions;

import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponse;
import com.paddy.btc.notifier.btc_notifier.ui.factories.CurrentPriceViewModelFactory;
import com.paddy.btc.notifier.btc_notifier.ui.views.ViewCurrentPrice;
import rx.functions.Action1;

public class UpdatePriceAction implements Action1<GetCurrentPriceResponse> {


    private final CurrentPriceViewModelFactory currentPriceViewModelFactory;
    private final ViewCurrentPrice cpViewCurrentPrice;

    public UpdatePriceAction(final ViewCurrentPrice cpViewCurrentPrice, final CurrentPriceViewModelFactory currentPriceViewModelFactory) {
        this.cpViewCurrentPrice = cpViewCurrentPrice;
        this.currentPriceViewModelFactory = currentPriceViewModelFactory;
    }

    @Override
    public void call(GetCurrentPriceResponse response) {
        cpViewCurrentPrice.updateDataModel(currentPriceViewModelFactory.getCurrentPriceModel(response));
    }
}
