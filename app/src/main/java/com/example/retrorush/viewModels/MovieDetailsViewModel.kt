package com.example.retrorush.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.retrorush.models.CastResponse
import com.example.retrorush.models.Movie
import com.example.retrorush.models.VideoResponse
import com.example.retrorush.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModelFactory(private val movieId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(movieId) as T;
    }
}

class MovieDetailsViewModel(movieId: Int) : ViewModel() {
    var movie = MutableLiveData<Movie>()
    var videoList = MutableLiveData<VideoResponse>()
    var castList = MutableLiveData<CastResponse>()

    init {
        viewModelScope.launch {
            movie.postValue(MovieRepository.fetchMovieDetails(movieId))
        }
        viewModelScope.launch {
            videoList.postValue(MovieRepository.fetchVideoList(movieId))
        }

        viewModelScope.launch {
            castList.postValue(MovieRepository.fetchCastList(movieId))
        }
    }
}