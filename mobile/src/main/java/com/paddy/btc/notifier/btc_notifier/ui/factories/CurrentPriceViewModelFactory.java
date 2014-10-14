package com.paddy.btc.notifier.btc_notifier.ui.factories;

import com.paddy.btc.notifier.btc_notifier.backend.models.BPI;
import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponse;
import com.paddy.btc.notifier.btc_notifier.backend.models.Time;
import com.paddy.btc.notifier.btc_notifier.ui.models.CurrentPriceViewModel;

public class CurrentPriceViewModelFactory {

    public CurrentPriceViewModel getCurrentPriceModel(GetCurrentPriceResponse getCurrentPriceResponse) {

        final CurrentPriceViewModel currentPriceViewModel = new CurrentPriceViewModel();

        final BPI bpiForUsd = getCurrentPriceResponse.getBPIs().getBpiForUsd();
        final String currentRate = bpiForUsd.getRate();
        final String getSymbol = bpiForUsd.getSymbol();

        final Time time = getCurrentPriceResponse.getTime();

        currentPriceViewModel.setRate(currentRate);
        currentPriceViewModel.setSymbol(getSymbol);
        currentPriceViewModel.setUpdatedAt(time.getUpdated());

        return currentPriceViewModel;
    }
}
