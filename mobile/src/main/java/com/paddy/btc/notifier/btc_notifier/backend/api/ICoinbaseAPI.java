package com.paddy.btc.notifier.btc_notifier.backend.api;

import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponse;
import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponseForISOCode;
import com.paddy.btc.notifier.btc_notifier.backend.models.SupportedCurrency;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;
import rx.Observable;

public interface ICoinbaseAPI {

    // current price
    @GET("/currentprice.json")
    Observable<GetCurrentPriceResponse> getCurrentBpi();

    // list of supported currencies
    @GET("/supported-currencies.json")
    void getSupportedCurrencies(Callback<List<SupportedCurrency>> callback);

    // current price in Currency ISO 4217
    @GET("/currentprice/{code}")
    Observable<GetCurrentPriceResponseForISOCode> getCurrentPriceResponseForISOCode();

    // historical data
}
