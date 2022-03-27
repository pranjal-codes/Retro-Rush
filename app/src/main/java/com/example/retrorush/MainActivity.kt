package com.example.retrorush

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.retrorush.adapter.MoviesAdapter
import com.example.retrorush.adapter.OnItemClickListener
import com.example.retrorush.databinding.ActivityMainBinding
import com.example.retrorush.models.Result
import com.example.retrorush.viewModels.MovieViewModel


class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var mBinding: ActivityMainBinding
    private var mMovieViewModel: MovieViewModel? = null
    private lateinit var adapter: MoviesAdapter

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

        setupScrollViewBottomListener()
        setupRecyclerView()
        setRepositoryObserver()
    }

    private fun setupRecyclerView() {
        MoviesAdapter(this, this).also {
            mBinding.moviesRecyclerView.adapter = it
            adapter = it
        }
    }

    private fun setRepositoryObserver() {
        mMovieViewModel!!.getMovieRepository().observe(this) {
//            val movieList: MutableList<Result>? = it;
            adapter.submitList(it)
        }
    }

    private fun setupScrollViewBottomListener() {
        mBinding.moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) { //1 for down
                    mMovieViewModel?.loadMovies()
                }
            }
        })
    }

    override fun onClick(result: Result) {
        val i = Intent(this, MovieDetailsActivity::class.java)
        i.putExtra("movieId", result.id)
        startActivity(i)
    }
}