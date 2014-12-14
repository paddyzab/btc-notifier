package com.paddy.btc.notifier.btc_notifier.ui;

import android.app.Activity;
import com.paddy.btc.notifier.btc_notifier.backend.actions.CustomCurrencyAction;
import com.paddy.btc.notifier.btc_notifier.storage.CurrencyProvider;
import dagger.Module;
import dagger.Provides;

@Module (
        injects = {
                CurrencyProvider.class,
                CustomCurrencyAction.class
        }
)
public class ActivityCurrentPriceModule {

    private final Activity activity;

    ActivityCurrentPriceModule(final Activity activity) {
        this.activity = activity;
    }

    @Provides Activity activity() {
        return activity;
    }

}
