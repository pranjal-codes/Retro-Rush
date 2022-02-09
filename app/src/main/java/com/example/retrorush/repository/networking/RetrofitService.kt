package com.example.retrorush.repository.networking

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

const val BASE_URL = "https://api.themoviedb.org/3/movie/"

val moshi = MoshiConverterFactory.create(
    Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
)

val retrofit: Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(moshi)
        .client(client)
        .build()


val retrofitService: MovieApi by lazy {
    retrofit.create(MovieApi::class.java)
}