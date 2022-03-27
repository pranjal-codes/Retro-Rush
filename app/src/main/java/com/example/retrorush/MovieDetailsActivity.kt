package com.example.retrorush

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.retrorush.adapter.CastAdapter
import com.example.retrorush.adapter.OnVideoItemClickListener
import com.example.retrorush.adapter.VideosAdapter
import com.example.retrorush.databinding.ActivityMovieDetailsBinding
import com.example.retrorush.models.Genres
import com.example.retrorush.models.Movie
import com.example.retrorush.models.Results
import com.example.retrorush.repository.TheMovieDatabaseAPI
import com.example.retrorush.viewModels.MovieDetailsViewModel
import com.example.retrorush.viewModels.MovieDetailsViewModelFactory
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class MovieDetailsActivity : AppCompatActivity(), OnVideoItemClickListener {

    private lateinit var mBinding: ActivityMovieDetailsBinding
    private lateinit var castAdapter: CastAdapter
    private lateinit var videosAdapter: VideosAdapter

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModelFactory(
            intent.extras!!.getInt("movieId")
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupRecyclerView()
        setUpObservers()
    }

    private fun setupRecyclerView() {
        CastAdapter(this).also {
            mBinding.castRecyclerView.adapter = it
            castAdapter = it
        }
        VideosAdapter(this,this).also {
            mBinding.videosRecyclerView.adapter = it
            videosAdapter = it
        }
    }

    private fun setUpObservers() {
        movieDetailsViewModel.movie.observe(this) {
            if (it != null) {
                with(mBinding)
                {

                    loadImagePoster(it.backdropPath,movieImage)
                    contentLayout.visibility = View.VISIBLE
                    titleText.text = it.title

                    setGenre(it.genres, genresText)
                    setRatingStar(it, ratingBar)

                    numOfVotes.text = it.voteCount.toString()

                    setReleaseDate(episodeText, it.releaseDate)
                    setRunTime(seasonText, it.runtime)

                    setLanguage(airDateText, it.originalLanguage)

                    overviewText.text = it.overview


                }
            } else {
                mBinding.contentLayout.visibility = View.GONE
            }
        }

        movieDetailsViewModel.castList.observe(this) {
            castAdapter.submitList(it.cast)
        }

        movieDetailsViewModel.videoList.observe(this) {
            videosAdapter.submitList(it.results)
        }
    }

    private fun loadImagePoster(posterPath: String?, movieImage: ImageView) {
        Picasso.with(this).load(TheMovieDatabaseAPI.getBackdropUrl(posterPath!!)).fit().centerCrop()
            .placeholder(R.drawable.sample)
            .error(R.drawable.sample)
            .into(movieImage);
    }

    private fun setLanguage(airDateText: TextView, originalLanguage: String?) {
        airDateText.text = Locale(originalLanguage).getDisplayLanguage(Locale("en"))
    }

    private fun setRunTime(seasonText: TextView, runtime: Int?) {
        val hoursText: String = appendZeroBeforeNumber(((runtime ?: 0) / 60f).toInt())
        val minutesText: String = appendZeroBeforeNumber(((runtime ?: 0) % 60f).toInt())
        val text = "$hoursText:$minutesText / $runtime min"
        seasonText.text = text
    }

    private fun setReleaseDate(episodeText: TextView, dateString: String?) {
        if (dateString.isNullOrBlank()) return
        val date = SimpleDateFormat(TheMovieDatabaseAPI.getRuntimeDateFormat()).parse(dateString)
        val pat =
            SimpleDateFormat().toLocalizedPattern().replace("\\W?[HhKkmsSzZXa]+\\W?".toRegex(), "")
        val localFormatter = SimpleDateFormat(pat, Locale.getDefault())
        episodeText.text = localFormatter.format(date)
    }

    private fun setRatingStar(it: Movie, ratingBar: RatingBar) {
        ratingBar.rating = (5 * ((it.voteAverage!! / TheMovieDatabaseAPI.MAX_RATING))).toFloat()
    }

    private fun setGenre(genres: ArrayList<Genres>, genresText: TextView) {
        if (genres == null) return

        val maxNumOfGenres = 3
        var text = ""
        val appendText = " / "

        val loopCount = if (genres.size <= maxNumOfGenres) genres.size else maxNumOfGenres
        for (i in 0 until loopCount) {
            text = text + genres[i].name + appendText
        }
        genresText.text = text.dropLast(appendText.length)
    }

    private fun appendZeroBeforeNumber(num: Int): String {
        return if (num < 10) "0$num" else num.toString()
    }

    override fun onClick(videos: Results) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${videos.key}"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=${videos.key}")
        )
        try {
            this.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            this.startActivity(webIntent)
        }
    }

}