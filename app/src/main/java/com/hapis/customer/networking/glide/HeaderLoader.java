package com.hapis.customer.networking.glide;

import android.support.annotation.NonNull;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.hapis.customer.HapisApplication;
import com.hapis.customer.networking.util.RestConstants;
import com.hapis.customer.ui.utils.AccessPreferences;
import com.hapis.customer.ui.utils.ApplicationConstants;

import java.io.InputStream;


class HeaderLoader extends BaseGlideUrlLoader<String> {


    protected HeaderLoader(ModelLoader<GlideUrl, InputStream> concreteLoader) {
        super(concreteLoader, null);
    }

    protected HeaderLoader(ModelLoader<GlideUrl, InputStream> concreteLoader, ModelCache<String, GlideUrl> modelCache) {
        super(concreteLoader, modelCache);
    }

    @Override
    protected String getUrl(String model, int width, int height, Options options) {
        return model;
    }


    @Override
    protected Headers getHeaders(String model, int width, int height, Options options) {
        String jwtHeader = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.NHANCE_JWT_HEADER, "");

        Headers REQUEST_HEADERS = new LazyHeaders.Builder()
                .addHeader(RestConstants.JWT_HEADER, jwtHeader)
                .build();
        return REQUEST_HEADERS;
    }


    @Override
    public boolean handles(@NonNull String s) {
        return true;
    }
}
