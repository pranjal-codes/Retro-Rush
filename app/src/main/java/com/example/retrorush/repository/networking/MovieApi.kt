package com.example.retrorush.repository.networking

import com.example.retrorush.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("popular")
    suspend fun fetchMovies(
        @Query("api_key") apiKey: String = "2b33206fb664d569611c1f3ef9bf1ce5",
        @Query("language") language: String = "en-US",
        @Query("page") pageNumber: Int = 1
    ): MovieResponse?
}