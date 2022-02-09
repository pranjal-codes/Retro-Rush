package com.example.retrorush.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrorush.models.MovieResponse
import com.example.retrorush.models.Result
import com.example.retrorush.repository.MovieRepository
import kotlinx.coroutines.launch


class MovieViewModel : ViewModel() {
    var mMovieData = MutableLiveData<MutableList<Result>>()
    var pageNumber = 1


    init {
        loadMovies()
    }

    fun getMovieRepository(): LiveData<MutableList<Result>> {
        return mMovieData
    }

    fun loadMovies() {
        viewModelScope.launch {
            try {
                val response = MovieRepository.fetchMovies(pageNumber)
                Log.d("funnnnn", response.toString())
                addResponseToList(response)
                pageNumber++ // For next call
            } catch (e: Exception) {
                Log.d("NO INTERNET MAYBE", "getPopularMovies: API called failed with ${e.message}")
            }
        }
    }

    private fun addResponseToList(response: MovieResponse?) {
        val result = response?.results
        val movieList = mutableListOf<Result>()
        movieList.addAll(mMovieData.value ?: emptyList())
        if (result != null) {
            movieList.addAll(result)
        }
        mMovieData.value = movieList
    }

}