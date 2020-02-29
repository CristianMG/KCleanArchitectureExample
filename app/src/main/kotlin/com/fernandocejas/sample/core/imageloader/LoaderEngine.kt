package com.fernandocejas.sample.core.imageloader

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.NotificationTarget

interface LoaderEngine {

    fun loadResource(
            imageView: ImageView,
            @DrawableRes
            resource: Int,
            @DrawableRes
            placeholder: Int? = null,
            @DrawableRes
            errorPlaceholder: Int? = null,
            success: ((drawable: Drawable) -> Unit)? = null,
            error: ((exception: GlideException?) -> Unit)? = null,
            @DimenRes
            corner: Int?)


    fun load(requestOptions: RequestOptions,
             url: String = "",
             success: ((drawable: Drawable) -> Unit)? = null,
             error: ((exception: GlideException?) -> Unit)? = null,
             ivImageView: ImageView,
             @DimenRes
             corner: Int?)


    fun load(requestOptions: RequestOptions,
             url: String = "",
             success: ((drawable: Bitmap) -> Unit)?,
             error: ((exception: GlideException?) -> Unit)?,
             notificationTarget: NotificationTarget)


    suspend fun loadBitmap(requestOptions: RequestOptions,
                           url: String = "",
                           diskCache: Boolean): Bitmap


}