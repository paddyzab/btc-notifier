package com.paddy.btc.notifier.btc_notifier;

import dagger.Component;

@Component(
       modules = BitcoinNotifierModule.class
)
public interface BitcoinNotifierComponent {
    BitcoinNotifier injectApplication(BitcoinNotifier application);
}
