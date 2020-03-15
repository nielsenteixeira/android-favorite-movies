package com.uni7.uni7movie.ui.movieitem

import android.util.Log
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
        var movieVote: TextView
        var movieOverview: TextView
        var movieImage: ImageView

        init {
            cv = itemView.findViewById<View>(R.id.cv) as CardView
            movieName = itemView.findViewById<View>(R.id.movie_name) as TextView
            movieVote = itemView.findViewById<View>(R.id.movie_vote) as TextView
            movieOverview = itemView.findViewById<View>(R.id.movie_overview) as TextView
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
        movieViewHolder.movieName.text = movies[i].title
        movieViewHolder.movieVote.text = movies[i].vote_average.toString()
        movieViewHolder.movieOverview.text = getCroppedOverview(movies[i].overview)
        DownloadImageTask(movieViewHolder.movieImage).execute("https://image.tmdb.org/t/p/w500" + movies[i].poster_path)


        movieViewHolder.cv.setOnClickListener(View.OnClickListener {
            Log.d(
                "Tag",
                "Cardview clicked" + movies[i].title
            )
        })
    }

    private fun getCroppedOverview(overview: String): String {
        val overviewLimitChar = 140
        val suffix = " (...)"
        if (overview.length > overviewLimitChar) {
            return overview.substring(0, overviewLimitChar) + suffix
        }
        return  overview
    }
}