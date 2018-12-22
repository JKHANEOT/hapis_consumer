package com.hapis.customer.ui.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import com.hapis.customer.utils.Application;

import java.util.Locale;

/**
 * Utility class to change app locale settings.
 */
public class LocaleUtils {

    private LocaleUtils() {
    }

    /**
     * Transform a language code in its corresponding locale. If the country code includes country settings,
     * it would be used, otherwise just the language is used.
     *
     * @param langCode Language code. Its follows the format de_DE, tr_TR, etc
     * @return Locale instance
     */
    public static Locale transform(String langCode) {

        if (langCode.length() > 2) {
            return new Locale(extractLocaleLanguage(langCode), extractLocaleCountry(langCode));
        } else {
            return new Locale(extractLocaleLanguage(langCode));
        }
    }

    /**
     * Extract country code from the langCode.
     *
     * @param langCode Language code. Its follows the format de_DE, tr_TR, etc
     * @return Locale instance
     */
    public static String extractLocaleCountry(String langCode) {
        return langCode.substring(3, 5);
    }

    /**
     * Extract language code from the langCode.
     *
     * @param langCode Language code. Its follows the format de, tr, de_DE, tr_TR, etc
     * @return extracted code
     */
    public static String extractLocaleLanguage(String langCode) {
        return langCode.substring(0, 2);
    }

    /**
     * Create a new Android configuration base on the Locale.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Configuration updateLocaleConfiguration(Context activity, Locale newLocale) {
        final Resources resources = activity.getResources();

        // Set default locale
        Locale.setDefault(newLocale);

        // Clone current settings and update locale configuration
        Configuration config = new Configuration(resources.getConfiguration());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(newLocale);
        } else {
            config.locale = newLocale;
        }
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        AccessPreferences.put(activity, ApplicationConstants.SELECTED_LOCALE_CODE, newLocale.getLanguage());
        Application.getInstance().setCurrentLanguage(newLocale.getLanguage());
        return config;
    }
}
