package com.uni7.uni7movie.service

import com.uni7.uni7movie.data.MoviesResult
import retrofit2.Call
import retrofit2.http.GET

interface TheMoviesDBService {
    @GET("/3/discover/movie?api_key=fd3cf77e05ffebc4738b13041a26fcec&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    fun getMovies() : Call<MoviesResult>
}