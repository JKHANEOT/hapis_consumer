package com.hapis.customer.ui.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.hapis.customer.logger.LOG;

import java.util.HashMap;
import java.util.Map;

public final class FontCache {

    private final String TAG = "FontCache";

    private static FontCache instance;

    private final Map<String, Typeface> fontMap = new HashMap<>();

    private FontCache() {
    }

    public static FontCache getInstance() {
        if (instance == null) {
            instance = new FontCache();
        }
        return instance;
    }

    public Typeface get(final String path) {
        return fontMap.get(path);
    }

    public Typeface put(final String path, final AssetManager assetManager) {

        if (assetManager == null) {
            LOG.e(TAG, "The assetManager cannot be null.");
            throw new IllegalArgumentException("The assetManager cannot be null.");
        }

        if (fontMap.containsKey(path)) {
            return get(path);
        }

        try {
            final Typeface typeface = Typeface.createFromAsset(assetManager, path);
            fontMap.put(path, typeface);
            return typeface;
        } catch (Exception ex) {
            LOG.e(TAG, "Can not load the font");
        }

        return null;
    }
}