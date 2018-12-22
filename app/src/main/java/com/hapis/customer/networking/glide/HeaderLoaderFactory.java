package com.hapis.customer.networking.glide;


import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

class HeaderLoaderFactory implements ModelLoaderFactory<String, InputStream> {

    @Override
    public ModelLoader<String, InputStream> build(MultiModelLoaderFactory multiFactory) {
        ModelLoader<GlideUrl, InputStream> loader = multiFactory.build(GlideUrl.class, InputStream.class);
        return new HeaderLoader(loader);
    }

    @Override
    public void teardown() { /* nothing to free */ }
}