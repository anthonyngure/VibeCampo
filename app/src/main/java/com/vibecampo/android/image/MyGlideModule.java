package com.vibecampo.android.image;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Anthony Ngure on 02/01/2017.
 * Email : anthonyngure25@gmail.com.
 * Company : VibeCampo Social Network..
 */

public class MyGlideModule implements GlideModule {
    /**
     * Lazily apply options to a {@link GlideBuilder} immediately before the Glide singleton is
     * isCreated.
     * <p>
     * <p>
     * This method will be called once and only once per implementation.
     * </p>
     *
     * @param context An Application {@link Context}.
     * @param builder The {@link GlideBuilder} that will be used to create Glide.
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
    }

    /**
     * Lazily register components immediately after the Glide singleton is isCreated but before any requests can be
     * started.
     * <p>
     * <p>
     * This method will be called once and only once per implementation.
     * </p>
     *
     * @param context An Application {@link Context}.
     * @param glide   The newly isCreated Glide singleton.
     */
    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
