package com.uni7.uni7movie.ui.movieitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.uni7.uni7movie.R
import com.uni7.uni7movie.data.MovieItem
import com.uni7.uni7movie.movieitem.DownloadImageTask


class MovieItemRecyclerViewAdapter internal constructor(private var movies: List<MovieItem>) :
    RecyclerView.Adapter<MovieItemRecyclerViewAdapter.MovieViewHolder>() {

    class MovieViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var cv: CardView
        var movieName: TextView
        var movieYear: TextView
        var movieImage: ImageView

        init {
            cv = itemView.findViewById<View>(R.id.cv) as CardView
            movieName = itemView.findViewById<View>(R.id.movie_name) as TextView
            movieYear = itemView.findViewById<View>(R.id.movie_year) as TextView
            movieImage = itemView.findViewById<View>(R.id.movie_image) as ImageView
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): MovieViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_movie_item, viewGroup, false)
        return MovieViewHolder(v)
    }

    override fun onBindViewHolder(
        movieViewHolder: MovieViewHolder,
        i: Int
    ) {
        movieViewHolder.movieName.text = movies[i].name
        movieViewHolder.movieYear.text = movies[i].year
        DownloadImageTask(movieViewHolder.movieImage).execute(movies[i].image_url)
    }

}