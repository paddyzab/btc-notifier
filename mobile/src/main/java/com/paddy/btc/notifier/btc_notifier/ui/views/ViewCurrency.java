package com.paddy.btc.notifier.btc_notifier.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.paddy.btc.notifier.btc_notifier.R;
import com.paddy.btc.notifier.btc_notifier.backend.models.SupportedCurrency;

public class ViewCurrency extends FrameLayout {

    @InjectView(R.id.textViewCountry)
    protected TextView textViewCountry;

    @InjectView(R.id.textViewCurrency)
    protected TextView textViewCurrency;

    public ViewCurrency(final Context context) {
        this(context, null);
    }

    public ViewCurrency(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewCurrency(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_currency, this);

        ButterKnife.inject(this);
    }

    public void displayCurrencyData(final SupportedCurrency supportedCurrency) {

        String country = supportedCurrency.getCountry();
        final String currency = supportedCurrency.getCurrency();

        textViewCountry.setText(country);
        textViewCurrency.setText(currency);
    }
}
