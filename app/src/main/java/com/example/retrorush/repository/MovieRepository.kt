package com.example.retrorush.repository

import com.example.retrorush.models.*
import com.example.retrorush.repository.networking.retrofitService


object MovieRepository {
    suspend fun fetchMovies(pageNumber: Int): MovieResponse? {
        // Get retrofit response
        return retrofitService.fetchMovies(pageNumber = pageNumber)
    }

    suspend fun fetchMovieDetails(movieId: Int): Movie? {
        // Get retrofit response
        return retrofitService.fetchMovieDetails(movieId = movieId)
    }

    suspend fun fetchVideoList(movieId: Int): VideoResponse? {
        // Get retrofit response
        return retrofitService.fetchVideoList(movieId = movieId)
    }

    suspend fun fetchCastList(movieId: Int): CastResponse? {
        // Get retrofit response
        return retrofitService.fetchCastList(movieId = movieId)
    }
}