package com.paddy.btc.notifier.btc_notifier.utils;

import com.paddy.btc.notifier.btc_notifier.backend.models.BPI;
import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponse;
import java.util.Locale;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class CurrentPriceTranslator {

    private final Locale locale;

    private final static String[] EUROZONE = {"aut", "bel", "cyp", "est", "fin", "fra", "deu", "grc", "irl",
            "ita", "lva", "ltu", "lux", "mlt", "nld", "prt", "svk", "svn", "esp"};

    //British Antarctic Territory (BAT) was deleted from the ISO3166-3 http://www.davros.org/misc/iso3166.html#disused
    private final static String[] POUNDZONE = {"gpb", "jey", "ggy", "imn", "sgs", "shn"};


    public CurrentPriceTranslator(final Locale locale) {
        this.locale = locale;
    }

    public String getFormatted(final GetCurrentPriceResponse getCurrentPriceResponse) {
        final String iso3Language = locale.getISO3Language();

        String formattedString;

        if (isEuro(iso3Language)) {
            final BPI bpiForEur = getCurrentPriceResponse.getBPIs().getBpiForEur();
            formattedString = bpiForEur.getRate() + escapeSymbol(bpiForEur.getSymbol());
        } else if (isPound(iso3Language)) {
            final BPI bpiForGpb = getCurrentPriceResponse.getBPIs().getBpiForGpb();
            formattedString = bpiForGpb.getRate() + escapeSymbol(bpiForGpb.getSymbol());
        } else {
            final BPI bpiForUsd = getCurrentPriceResponse.getBPIs().getBpiForUsd();
            formattedString = escapeSymbol(bpiForUsd.getSymbol()) + bpiForUsd.getRate();
        }

        return formattedString;
    }

    private String escapeSymbol(final String symbol) {
        return StringEscapeUtils.unescapeHtml(symbol);
    }

    private boolean isEuro(String iso3Language) {
        for (String string : EUROZONE) {
            if (StringUtils.equals(iso3Language, string)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPound(String iso3Language) {
        for (String string : POUNDZONE) {
            if (StringUtils.equals(iso3Language, string)) {
                return true;
            }
        }
        return false;
    }
}
