package com.uni7.uni7movie.ui.movieitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.uni7.uni7movie.R
import com.uni7.uni7movie.data.MovieItem
import com.uni7.uni7movie.data.MoviesResult
import com.uni7.uni7movie.service.NetworkUtils
import com.uni7.uni7movie.service.TheMoviesDBService
import retrofit2.Call
import retrofit2.Response


class MovieItemFragment : Fragment() {

    private var columnCount = 1
    private val httpStatusNotFound = 404
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
        if (view is RecyclerView) {

            with(view) {
                hasFixedSize()
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
            }

            loadMovies(view)
        }


        return view
    }

    private fun loadMovies(view: RecyclerView) {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance()

        val endpoint = retrofitClient.create(TheMoviesDBService::class.java)
        val callback = endpoint.getMovies()
        callback.enqueue(object: retrofit2.Callback<MoviesResult?> {
            override fun onFailure(call: Call<MoviesResult?>, t: Throwable) {
                Snackbar.make(view, "Falha ao carregar filmes!", Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
            }

            override fun onResponse(call: Call<MoviesResult?>, response: Response<MoviesResult?>) {
                if (response.code() == httpStatusNotFound) {
                    view.adapter = MovieItemRecyclerViewAdapter(listOf())
                } else {
                    response.body()?.let {
                        val movies: List<MovieItem> = it.results
                        view.adapter = MovieItemRecyclerViewAdapter(movies)
                    }
                }
            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: MovieItem?)
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MovieItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
