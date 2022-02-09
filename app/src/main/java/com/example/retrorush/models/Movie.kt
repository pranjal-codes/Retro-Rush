package com.example.retrorush.models

data class Genre(
    var id: Int = 0,
    var name: String? = null
) {
}

data class ProductionCompany(
    var id: Int = 0,
    var logo_path: String? = null,
    var name: String? = null,
    var origin_country: String? = null
) {}

data class ProductionCountry(
    var iso_3166_1: String? = null,
    var name: String? = null
) {
}

data class SpokenLanguage(
    var english_name: String? = null,
    var iso_639_1: String? = null,
    var name: String? = null
) {
}

data class Movie(
    var adult: Boolean = false,
    var backdrop_path: String? = null,
    var belongs_to_collection: Any? = null,
    var budget: Int = 0,
    var genres: ArrayList<Genre>? = null,
    var homepage: String? = null,
    var id: Int = 0,
    var imdb_id: String? = null,
    var original_language: String? = null,
    var original_title: String? = null,
    var overview: String? = null,
    var popularity: Double = 0.0,
    var poster_path: String? = null,
    var production_companies: ArrayList<ProductionCompany>? = null,
    var production_countries: ArrayList<ProductionCountry>? = null,
    var release_date: String? = null,
    var revenue: Int = 0,
    var runtime: Int = 0,
    var spoken_languages: ArrayList<SpokenLanguage>? = null,
    var status: String? = null,
    var tagline: String? = null,
    var title: String? = null,
    var video: Boolean = false,
    var vote_average: Double = 0.0,
    var vote_count: Int = 0
) {
}

