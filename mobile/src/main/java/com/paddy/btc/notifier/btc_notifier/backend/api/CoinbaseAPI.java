package com.paddy.btc.notifier.btc_notifier.backend.api;

import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponse;
import rx.Observable;

public class CoinbaseAPI implements ICoinbaseAPI {

    @Override
    public Observable<GetCurrentPriceResponse> getCurrentBpi() {
        return null;
    }
}
