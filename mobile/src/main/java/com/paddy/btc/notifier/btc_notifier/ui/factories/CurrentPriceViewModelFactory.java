package com.paddy.btc.notifier.btc_notifier.ui.factories;

import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponse;
import com.paddy.btc.notifier.btc_notifier.backend.models.Time;
import com.paddy.btc.notifier.btc_notifier.storage.CurrencyProvider;
import com.paddy.btc.notifier.btc_notifier.ui.models.CurrentPriceViewModel;
import com.paddy.btc.notifier.btc_notifier.utils.CurrentPriceTranslator;

public class CurrentPriceViewModelFactory {

    private final CurrentPriceTranslator priceTranslator;

    public CurrentPriceViewModelFactory(final CurrencyProvider currencyProvider) {
        priceTranslator = new CurrentPriceTranslator(currencyProvider);
    }


    public CurrentPriceViewModel getCurrentPriceModel(final GetCurrentPriceResponse getCurrentPriceResponse) {
        final CurrentPriceViewModel currentPriceViewModel = new CurrentPriceViewModel();

        final String formattedRate = priceTranslator.getFormatted(getCurrentPriceResponse);
        final Time time = getCurrentPriceResponse.getTime();

        currentPriceViewModel.setFormattedRate(formattedRate);
        currentPriceViewModel.setUpdatedAt(time.getUpdated());

        return currentPriceViewModel;
    }
}
