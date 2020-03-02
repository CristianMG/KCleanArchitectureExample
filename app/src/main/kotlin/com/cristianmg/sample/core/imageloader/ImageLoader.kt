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
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestOptions
import com.cristianmg.sample.R
import timber.log.Timber

class ImageLoader(val context: Context, val engineLoader: LoaderEngine) {

    fun network(init: FromNetwork.() -> Unit): FromNetwork {
        return FromNetwork(init)
    }


    fun resource(init: FromResource.() -> Unit): FromResource {
        return FromResource(init)
    }


    inner class FromResource private constructor() {

        constructor(init: FromResource.() -> Unit) : this() {
            init()
        }

        @DrawableRes
        var resource: Int? = null


        private var success: ((drawable: Drawable) -> Unit)? = null
        private var error: ((exception: GlideException?) -> Unit)? = null


        fun resource(init: () -> Int) {
            resource = init()
        }


        fun successCallback(init: () -> ((drawable: Drawable) -> Unit)) = apply { this.success = init() }
        fun errorCallback(init: () -> ((exception: GlideException?) -> Unit)) = apply { this.error = init() }


        /**
         * Load image with engine was configured by dependency injection is more efficient to load big images and can round the image
         * @param imageView ImageView Image to load the resource
         */
        fun loadWithEngine(imageView: ImageView,
                           @DimenRes corner: Int? = null,
                           @DrawableRes placeholder: Int? = null,
                           @DrawableRes errorPlaceholder: Int? = null) {
            resource?.let {
                engineLoader.loadResource(imageView = imageView, resource = it, placeholder = placeholder, errorPlaceholder = errorPlaceholder, corner = corner,success = success,error = error)
            } ?: kotlin.run {
                Timber.e("The res cannot be null")
            }
        }

        /**
         * Load image resource with native android functions cannot round the image
         * @param imageView ImageView Image to load the resource
         */
        fun load(imageView: ImageView) {
            if (resource == null) {
                Timber.e("The res cannot be null")
                return
            }
            resource?.let {
                imageView.setTag(IMAGE_LOADER_TAG, StateLoaded.LoadedFromResource(it, false))
                imageView.setImageResource(it)
            }
        }

        fun loadBackground(view: View) {
            if (resource == null) {
                Timber.e("The res cannot be 0")
                return
            }
            resource?.let {
                view.setTag(IMAGE_LOADER_BACKGROUND_TAG, StateLoaded.LoadedFromResource(it, true))
                view.setBackgroundResource(it)
            }
        }
    }

    inner class FromNetwork private constructor() {

        constructor(init: FromNetwork.() -> Unit) : this() {
            init()
        }

        private var url: String = ""
        private var placeHolder: Int? = null
        private var errorPlaceHolder: Int? = null
        private var success: ((drawable: Drawable) -> Unit)? = null
        private var error: ((exception: GlideException?) -> Unit)? = null
        private var diskCache: Boolean = false
        @DimenRes
        private var cornerSize: Int? = null

        fun url(init: () -> String) = apply { this.url = init() }
        fun error(init: () -> Int) = apply { this.errorPlaceHolder = init() }
        fun placeholder(init: () -> Int) = apply { this.placeHolder = init() }
        fun cornerSize(init: () -> Int) = apply { this.cornerSize = init() }
        fun successCallback(init: () -> ((drawable: Drawable) -> Unit)) = apply { this.success = init() }
        fun errorCallback(init: () -> ((exception: GlideException?) -> Unit)) = apply { this.error = init() }
        fun diskCache(init: () -> Boolean) = apply { this.diskCache = init() }

        fun load(imageView: ImageView) {
            Timber.d("Loading image $url in imageview ${imageView.id}")
            val options = getRequestOption()
            engineLoader.load(options, url, success, error, imageView, cornerSize)
        }

        suspend fun loadBitmap(): Bitmap {
            Timber.d("Loading bitmap image $url")
            val options = getRequestOption()
            return engineLoader.loadBitmap(options, url, diskCache)
        }

        private fun getRequestOption(): RequestOptions {
            val options = RequestOptions()
            placeHolder?.let { options.placeholder(it) }
            errorPlaceHolder?.let { options.error(it) }
            placeHolder?.let { options.placeholder(it) }
            return options
        }
    }


    /**
     * Class which represent the last state was loaded in imageviewloader
     *
     * **/
    sealed class StateLoaded {

        data class LoadedFromNetwork(
                val url: String,
                val placeHolder: Int? = null,
                val errorPlaceholder: Int? = null,
                val success: Boolean,
                val messageError: String = "") : StateLoaded() {


            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                other as LoadedFromNetwork
                if (url != other.url) return false
                if (placeHolder != other.placeHolder) return false
                if (errorPlaceholder != other.errorPlaceholder) return false
                if (success != other.success) return false
                return true
            }

            override fun hashCode(): Int {
                var result = url.hashCode()
                result = 31 * result + (placeHolder ?: 0)
                result = 31 * result + (errorPlaceholder ?: 0)
                result = 31 * result + success.hashCode()
                return result
            }
        }

        data class LoadedFromResource(val resource: Int, val background: Boolean) : StateLoaded()
    }


    companion object {
        const val IMAGE_LOADER_TAG = R.id.image_loader_tag
        const val IMAGE_LOADER_BACKGROUND_TAG = R.id.image_loader_background_tag

        fun getTagWithImageView(iv: ImageView): StateLoaded? =
                (iv.getTag(IMAGE_LOADER_TAG) as? StateLoaded)

    }
}