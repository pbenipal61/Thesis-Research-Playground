package com.example.thesisresearchplayground.di.module

import android.content.Context
import com.example.thesisresearchplayground.BuildConfig
import com.example.thesisresearchplayground.data.api.ApiService
import com.example.thesisresearchplayground.utils.NetworkHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.thesisresearchplayground.data.api.ApiHelper
import com.example.thesisresearchplayground.data.api.ApiHelperImpl
import org.koin.android.ext.koin.androidContext

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideOkHttpClient() = if(BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
} else OkHttpClient.Builder().build()


private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()


private fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), BuildConfig.BASE_URL) }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }
    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }
}