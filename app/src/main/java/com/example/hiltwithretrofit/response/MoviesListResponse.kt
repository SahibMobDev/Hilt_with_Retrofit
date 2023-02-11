package com.example.hiltwithretrofit.response


import com.google.gson.annotations.SerializedName

data class MoviesListResponse(
    @SerializedName("page")
    val page: Int, // 1
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int, // 982
    @SerializedName("total_results")
    val totalResults: Int // 19629
) {
    data class Result(
        @SerializedName("adult")
        val adult: Boolean, // false
        @SerializedName("backdrop_path")
        val backdropPath: String, // /ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg
        @SerializedName("genre_ids")
        val genreIds: List<Int>,
        @SerializedName("id")
        val id: Int, // 297761
        @SerializedName("original_language")
        val originalLanguage: String, // en
        @SerializedName("original_title")
        val originalTitle: String, // Suicide Squad
        @SerializedName("overview")
        val overview: String, // From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.
        @SerializedName("popularity")
        val popularity: Double, // 48.261451
        @SerializedName("poster_path")
        val posterPath: String, // /e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg
        @SerializedName("release_date")
        val releaseDate: String, // 2016-08-03
        @SerializedName("title")
        val title: String, // Suicide Squad
        @SerializedName("video")
        val video: Boolean, // false
        @SerializedName("vote_average")
        val voteAverage: Double, // 5.91
        @SerializedName("vote_count")
        val voteCount: Int // 1466
    )
}