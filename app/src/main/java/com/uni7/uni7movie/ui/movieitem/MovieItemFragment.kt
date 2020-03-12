package com.uni7.uni7movie.ui.movieitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uni7.uni7movie.R
import com.uni7.uni7movie.data.MovieItem


class MovieItemFragment : Fragment() {

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {

            with(view) {
                hasFixedSize()
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter =
                    MovieItemRecyclerViewAdapter(
                        getMovies()
                    )
            }
        }
        return view
    }

    private fun getMovies(): ArrayList<MovieItem> {
        val movies = ArrayList<MovieItem>()
        movies.add(
            MovieItem(
                1,
                "The Shawshank Redemption",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/9fSGtPOp3MlFfynLEjYjlOfenbk.jpg",
                "1994"
            )
        )
        movies.add(
            MovieItem(
                2,
                "The Godfather",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/9fSGtPOp3MlFfynLEjYjlOfenbk.jpg",
                "1972"
            )
        )
        movies.add(
            MovieItem(
                3,
                "Forrest Gump",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/9fSGtPOp3MlFfynLEjYjlOfenbk.jpg",
                "1994"
            )
        )
        movies.add(
            MovieItem(
                4,
                "Forrest Gump",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/9fSGtPOp3MlFfynLEjYjlOfenbk.jpg",
                "1994"
            )
        )
        movies.add(
            MovieItem(
                5,
                "Forrest Gump",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/9fSGtPOp3MlFfynLEjYjlOfenbk.jpg",
                "1994"
            )
        )

        return movies
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: MovieItem?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MovieItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
