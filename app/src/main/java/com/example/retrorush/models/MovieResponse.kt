package com.example.retrorush.models

data class Result(
    val adult: Boolean,
    val backdrop_path: String?, // API returns null sometimes
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
) {
    val imageUrl: String
        get() {
            return "https://image.tmdb.org/t/p/w500/$poster_path"
        }
}

data class MovieResponse(
    val page: Int,
    var results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)
