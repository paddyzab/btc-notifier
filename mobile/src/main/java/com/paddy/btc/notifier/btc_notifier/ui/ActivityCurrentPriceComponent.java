package com.paddy.btc.notifier.btc_notifier.ui;

import com.paddy.btc.notifier.btc_notifier.BitcoinNotifierComponent;
import dagger.Component;

@Component (
        dependencies = BitcoinNotifierComponent.class,
        modules = ActivityCurrentPriceModule.class
)

public interface ActivityCurrentPriceComponent {

}
