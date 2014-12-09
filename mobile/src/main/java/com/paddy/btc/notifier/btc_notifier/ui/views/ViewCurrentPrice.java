package com.paddy.btc.notifier.btc_notifier.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.paddy.btc.notifier.btc_notifier.R;
import com.paddy.btc.notifier.btc_notifier.ui.models.CurrentPriceViewModel;

public class ViewCurrentPrice extends FrameLayout {

    @InjectView(R.id.tvCurrentPrice)
    protected TextView tvCurrentPrice;

    @InjectView(R.id.tvUpdatedAt)
    protected TextView tvUpdatedAt;

    public ViewCurrentPrice(Context context) {
        this(context, null);
    }

    public ViewCurrentPrice(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewCurrentPrice(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_current_price, this);

        ButterKnife.inject(this);

    }

    public void updateDataModel(CurrentPriceViewModel currentPriceViewModel) {
        tvCurrentPrice.setText(currentPriceViewModel.getFormattedRate());
        tvUpdatedAt.setText(currentPriceViewModel.getUpdatedAt());
    }
}
