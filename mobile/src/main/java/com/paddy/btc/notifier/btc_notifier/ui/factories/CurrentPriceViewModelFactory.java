package com.paddy.btc.notifier.btc_notifier.ui.factories;

import android.content.Context;
import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponse;
import com.paddy.btc.notifier.btc_notifier.backend.models.Time;
import com.paddy.btc.notifier.btc_notifier.ui.models.CurrentPriceViewModel;
import com.paddy.btc.notifier.btc_notifier.utils.CurrentPriceTranslator;
import java.util.Locale;

public class CurrentPriceViewModelFactory {

    private final CurrentPriceTranslator priceTranslator;

    public CurrentPriceViewModelFactory(final Context context) {
        final Locale locale = context.getResources().getConfiguration().locale;
        priceTranslator = new CurrentPriceTranslator(locale);
    }


    public CurrentPriceViewModel getCurrentPriceModel(GetCurrentPriceResponse getCurrentPriceResponse) {
        final CurrentPriceViewModel currentPriceViewModel = new CurrentPriceViewModel();

        final String formattedRate = priceTranslator.getFormatted(getCurrentPriceResponse);
        final Time time = getCurrentPriceResponse.getTime();

        currentPriceViewModel.setFormattedRate(formattedRate);
        currentPriceViewModel.setUpdatedAt(time.getUpdated());

        return currentPriceViewModel;
    }
}
