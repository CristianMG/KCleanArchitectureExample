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