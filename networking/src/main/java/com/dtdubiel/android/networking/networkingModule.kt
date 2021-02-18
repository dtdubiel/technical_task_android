package com.dtdubiel.android.networking

import com.dtdubiel.android.networking.BuildConfig.BASE_URL
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val AUTH = "Authorization"
private const val BEARER = "Bearer"

val networkingModule = module {

    single {
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain ->
                val original: Request = chain.request()

                val request: Request = original.newBuilder()
                    .header(AUTH, "$BEARER ${BuildConfig.TOKEN}")
                    .method(original.method, original.body)
                    .build()

                chain.proceed(request)
            })
            .addInterceptor(get<HttpLoggingInterceptor>(named("LoggingInterceptor")))
            .build()
    }

    single<GoRestApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(GoRestApi::class.java)
    }

    single(named("LoggingInterceptor")) {
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    single {
        RemoteDataSource(get())
    }

}