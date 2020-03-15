package com.uni7.uni7movie

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.uni7.uni7movie.data.MovieItem
import com.uni7.uni7movie.service.NetworkUtils
import com.uni7.uni7movie.service.TheMoviesDBService
import retrofit2.Call
import retrofit2.Response

class MovieActivity : AppCompatActivity() {

    private val httpStatusNotFound = 404
    val baseImageUrl = "https://image.tmdb.org/t/p/w500"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        loadMovie()
    }

    private fun loadMovie() {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance()

        val endpoint = retrofitClient.create(TheMoviesDBService::class.java)
        val movieId = intent.getIntExtra("MOVIE_ID", 0).toLong()
        val callback = endpoint.getMovie(movieId)
        callback.enqueue(object: retrofit2.Callback<MovieItem?> {
            override fun onFailure(call: Call<MovieItem?>, t: Throwable) {
                Toast.makeText(applicationContext, "Falha ao carregar filme.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MovieItem?>, response: Response<MovieItem?>) {
                if (response.code() == httpStatusNotFound) {
                    Toast.makeText(applicationContext, "Filme não encontrado.", Toast.LENGTH_LONG).show()
                } else {
                    response.body()?.let {

                        val movieImage = findViewById<ImageView>(R.id.movie_image)
                        val textName = findViewById<TextView>(R.id.movie_name)
                        val textRate = findViewById<TextView>(R.id.movie_vote)
                        val textOverview = findViewById<TextView>(R.id.movie_overview)

                        textName.text = it.title
                        textRate.text = it.vote_average.toString()
                        textOverview.text = it.overview
                        Picasso.get().load(baseImageUrl + it.poster_path).into(movieImage)
                    }
                }
            }

        })
    }
}
