/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.sample.core.di

import android.content.Context
import androidx.loader.content.Loader
import com.fernandocejas.sample.AndroidApplication
import com.fernandocejas.sample.BuildConfig
import com.fernandocejas.sample.core.di.qualifiers.Cloud
import com.fernandocejas.sample.core.di.qualifiers.Disk
import com.fernandocejas.sample.core.imageloader.GlideLoader
import com.fernandocejas.sample.core.imageloader.ImageLoader
import com.fernandocejas.sample.core.imageloader.LoaderEngine
import com.fernandocejas.sample.data.AppDatabase
import com.fernandocejas.sample.data.repository.farm.FarmRepository
import com.fernandocejas.sample.data.repository.mapping.DateFormat
import com.fernandocejas.sample.data.repository.task.TaskRepository
import com.fernandocejas.sample.data.repository.user.SessionRepository
import com.fernandocejas.sample.data.repository.user.UserRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://data.ct.gov")
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }


    @Provides
    @Singleton
    fun provideEngineImageLoader(context: Context): LoaderEngine = GlideLoader(context)

    @Provides
    @Singleton
    fun provideImageLoader(context: Context, engine: LoaderEngine): ImageLoader = ImageLoader(context, engine)


    @Provides
    @Singleton
    fun provideDateFormat(): DateFormat = DateFormat.instance

    @Provides
    @Singleton
    fun providesAppDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun providesUserRepository(dataSource: UserRepository.Disk): UserRepository = dataSource

    @Provides
    @Singleton
    fun providesTaskRepository(dataSource: TaskRepository.Disk): TaskRepository = dataSource

    @Provides
    @Singleton
    fun providesSessionRepository(dataSource: SessionRepository.Disk): SessionRepository = dataSource

    @Provides
    @Singleton
    @Disk
    fun provideFarmRepositoryDisk(dataSource: FarmRepository.Disk): FarmRepository = dataSource

    @Provides
    @Singleton
    @Cloud
    fun provideFarmRepositoryNetwork(dataSource: FarmRepository.Network): FarmRepository = dataSource
}
