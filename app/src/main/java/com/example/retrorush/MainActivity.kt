package com.example.retrorush

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrorush.databinding.ActivityMainBinding
import com.example.retrorush.models.MovieResponse
import com.example.retrorush.viewModels.MovieViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private var mMovieViewModel: MovieViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Used viewBinding to bind this' activity layout
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        //Setting up the viewModel
        mMovieViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MovieViewModel::class.java]

//        setupRecyclerView()
        setRepositoryObserver()
    }

    private fun setRepositoryObserver() {
        mMovieViewModel!!.getMovieRepository()?.observe(this) {

            val movieList: MovieResponse? = it;

        }
    }
}