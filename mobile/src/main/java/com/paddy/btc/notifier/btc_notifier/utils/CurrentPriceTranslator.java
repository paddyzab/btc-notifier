package com.paddy.btc.notifier.btc_notifier.utils;

import android.content.Context;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;

public class CurrentPriceTranslator {

    private final Context context;

    private final static String[] EUROZONE = {"AUT", "BEL", "CYP", "EST", "FIN", "FRA", "DEU", "GRC", "IRL",
            "ITA", "LVA", "LTU", "LUX", "MLT", "NLD", "PRT", "SVK", "SVN", "ESP"};

    //British Antarctic Territory (BAT) was deleted from the ISO3166-3 vide http://www.davros.org/misc/iso3166.html#disused
    private final static String[] POUNDZONE = {"GPB", "JEY", "GGY", "IMN", "SGS", "SHN"};


    public CurrentPriceTranslator(final Context context) {
        this.context = context;
    }

    public String getFormatted() {
        Locale locale = context.getResources().getConfiguration().locale;
        String iso3Language = locale.getISO3Language();

        if (isEuro(iso3Language)) {
            //format with Euro
        } else if (isPound(iso3Language)) {
            // format with GPB
        } else {
            // format with USD
        }

        return "";
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
