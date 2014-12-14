package com.paddy.btc.notifier.btc_notifier;

import android.app.Application;

public class BitcoinNotifier extends Application {
    private BitcoinNotifierComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        this.component = Dagger_BitcoinNotifierComponent.builder()
                .bitcoinNotifierModule(new BitcoinNotifierModule(this))
                .build();
        component.injectApplication(this);
    }

    public BitcoinNotifierComponent getComponent() {
        return component;
    }
}
