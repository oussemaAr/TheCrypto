package io.studio.thecrypto.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient {
    companion object {
        @Volatile
        private lateinit var instance: RateService

        fun getInstance(): RateService {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    val interceptor = HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    }
                    val client = OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .build()
                    instance = Retrofit.Builder()
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(CoroutineCallAdapterFactory())
                        .baseUrl("https://bitpay.com/")
                        .build()
                        .create(RateService::class.java)
                }
                return instance
            }
        }
    }
}