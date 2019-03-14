package com.example.hyeyeon.androidkotlinmvvm.module


import com.example.hyeyeon.androidkotlinmvvm.common.ResourceProvider
import com.example.hyeyeon.androidkotlinmvvm.data.handler.SearchEventHandler
import com.example.hyeyeon.androidkotlinmvvm.data.repository.SearchRepositoryImpl
import com.example.hyeyeon.androidkotlinmvvm.data.service.SearchDataSource
import com.example.hyeyeon.androidkotlinmvvm.view.activity.SearchActivity
import com.example.hyeyeon.androidkotlinmvvm.viewmodel.SearchViewModel
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

val appModule = module {
    viewModel { (handler: SearchEventHandler) -> SearchViewModel(handler, get(), get()) }
    single { ResourceProvider(get()) }
    single { SearchRepositoryImpl(get()) }
}

val networkModule = module {
    single { createOkHttpClient() }
    single { createWebService<SearchDataSource>(get(), DataSourceProperties.BASE_URL) }
}

object DataSourceProperties {
    const val BASE_URL = "https://openapi.naver.com/v1/search/"
}

inline fun <reified S> createWebService(okHttpClient: OkHttpClient, url: String): S {
    return GsonBuilder().setLenient().create().let { gson ->
        Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(NullOnEmptyConverterFactory())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().let { retrofit ->
                    retrofit.create(S::class.java)
                }
    }
}

fun createOkHttpClient(): OkHttpClient = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }.let {
    return OkHttpClient.Builder().addInterceptor(it).build()
}

class NullOnEmptyConverterFactory : Converter.Factory() {
    fun converterFactory() = this

    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object : Converter<ResponseBody, Any?> {
        val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
        override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
    }
}