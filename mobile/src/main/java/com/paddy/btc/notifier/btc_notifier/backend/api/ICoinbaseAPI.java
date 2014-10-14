package com.paddy.btc.notifier.btc_notifier.backend.api;

import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponse;
import retrofit.http.GET;
import rx.Observable;

public interface ICoinbaseAPI {

    // current price
    @GET("/currentprice.json")
    Observable<GetCurrentPriceResponse> getCurrentBpi();

    // current price in Currency ISO 4217
    //  @GET("/currentprice/{code}")
    //  Observable<GetCurrentPriceResponseForISOCode> getCurrentPriceResponseForISOCode();

    // historical data
}
