/*
 *
 *  * Copyright 2020 Cristian Menárguez González
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.cristianmg.sample.core.imageloader

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.DimenRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.NotificationTarget
import timber.log.Timber


class GlideLoader(val context: Context) : LoaderEngine {

    override fun loadResource(imageView: ImageView, resource: Int,
                              placeholder: Int?, errorPlaceholder: Int?,
                              success: ((drawable: Drawable) -> Unit)?, error: ((exception: GlideException?) -> Unit)?,
                              corner: Int?) {

        val options = RequestOptions()
        placeholder?.let { options.placeholder(it) }
        errorPlaceholder?.let { options.error(it) }

        var requestBuilder = Glide.with(context)
                .load(resource)
                .apply(options)
                .addListener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        Timber.d("onLoadSuccess resource= $resource")
                        success?.invoke(resource)
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        error?.invoke(e)
                        Timber.d("onLoadFailed resource= $resource")
                        return false
                    }
                })

        corner?.let {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                requestBuilder = requestBuilder.transform(CenterCrop())
                        .transform(RoundedCorners(context.resources.getDimensionPixelSize(it)))
                        .centerCrop()
            } else {

                val gd = GradientDrawable()
                        .apply {
                            cornerRadius = context.resources.getDimension(it)
                        }

                imageView.background = gd
                imageView.clipToOutline = true
            }
        }
        requestBuilder.into(imageView)
    }

    override fun load(requestOptions: RequestOptions,
                      url: String,
                      success: ((drawable: Drawable) -> Unit)?,
                      error: ((exception: GlideException?) -> Unit)?,
                      ivImageView: ImageView,
                      @DimenRes
                      cornerSize: Int?) {

        if (url.isEmpty()) {
            Timber.e("The url cannot be null")
            return
        }

        var requestBuilder = Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .addListener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        Timber.d("onLoadSuccess url= $url")
                        success?.invoke(resource)
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        error?.invoke(e)
                        Timber.d("onLoadFailed url= $url")
                        return false
                    }
                })

        cornerSize?.let {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                requestBuilder = requestBuilder.transform(CenterCrop())
                        .transform(RoundedCorners(context.resources.getDimensionPixelSize(it)))
                        .centerCrop()
            } else {

                val gd = GradientDrawable()
                        .apply {
                            cornerRadius = context.resources.getDimension(it)
                        }

                ivImageView.background = gd
                ivImageView.clipToOutline = true
            }
        }
        requestBuilder.into(ivImageView)
    }


    override fun load(requestOptions: RequestOptions, url: String,
                      success: ((drawable: Bitmap) -> Unit)?,
                      error: ((exception: GlideException?) -> Unit)?,
                      notificationTarget: NotificationTarget) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .addListener(object : RequestListener<Bitmap> {
                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        Timber.d("onLoadSuccess url= $url")
                        resource?.let { success?.invoke(resource) }
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        Timber.d("onLoadFailed url= $url")
                        error?.invoke(e)
                        return false
                    }
                })

                .into(notificationTarget)
    }


    override suspend fun loadBitmap(requestOptions: RequestOptions, url: String, diskCache: Boolean): Bitmap {
        val request = Glide.with(context)
                .asBitmap()
                .load(url)

        if (diskCache)
            request.diskCacheStrategy(DiskCacheStrategy.DATA)


        return request.submit()
                .get()
    }

}