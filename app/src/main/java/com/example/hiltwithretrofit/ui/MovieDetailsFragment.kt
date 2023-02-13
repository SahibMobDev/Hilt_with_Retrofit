package com.example.hiltwithretrofit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.example.hiltwithretrofit.R
import com.example.hiltwithretrofit.databinding.FragmentMovieDetailsBinding
import com.example.hiltwithretrofit.repository.ApiRepository
import com.example.hiltwithretrofit.response.MovieDetailResponse
import com.example.hiltwithretrofit.utils.Constants.POSTER_BASEURL
import com.example.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding: FragmentMovieDetailsBinding
        get() = _binding!!

    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MoviesViewModel by viewModels()
    private var movieId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = args.movieId
        if (movieId > 0){
            viewModel.loadDetailsMovie(movieId)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            viewModel.detailsMovie.observe(viewLifecycleOwner) { response ->
                val moviePosterURL = POSTER_BASEURL + response.posterPath
                imgMovie.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                imgMovieBack.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }

                tvMovieTitle.text = response.title
                tvMovieTagLine.text = response.tagline
                tvMovieDateRelease.text = response.releaseDate
                tvMovieRating.text = response.voteAverage.toString()
                tvMovieRuntime.text = response.runtime.toString()
                tvMovieBudget.text = response.budget.toString()
                tvMovieRevenue.text = response.revenue.toString()
                tvMovieOverview.text = response.overview
            }

            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    prgBarMovies.visibility = View.VISIBLE
                } else {
                    prgBarMovies.visibility = View.INVISIBLE
                }
            }
        }
            }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}