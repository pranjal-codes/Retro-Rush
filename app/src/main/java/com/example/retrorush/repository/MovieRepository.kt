package com.example.retrorush.repository

import com.example.retrorush.models.MovieResponse
import com.example.retrorush.repository.networking.retrofitService


object MovieRepository {
    suspend fun fetchMovies(pageNumber: Int): MovieResponse? {
        // Get retrofit response
        return retrofitService.fetchMovies(pageNumber = pageNumber)
    }
}