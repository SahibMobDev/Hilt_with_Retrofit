package com.example.hiltwithretrofit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.example.hiltwithretrofit.R
import com.example.hiltwithretrofit.databinding.FragmentMovieDetailsBinding
import com.example.hiltwithretrofit.repository.ApiRepository
import com.example.hiltwithretrofit.response.MovieDetailResponse
import com.example.hiltwithretrofit.utils.Constants.POSTER_BASEURL
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    @Inject
    lateinit var apiRepository: ApiRepository

    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.movieId
        apiRepository.getMovieDetails(id).enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                binding.apply {
                    prgBarMovies.visibility = View.INVISIBLE
                    when(response.code()) {
                        200 -> {
                            response.body()?.let {
                                val moviePoster = POSTER_BASEURL + it.posterPath
                                tvMovieBudget.text = it.budget.toString()
                                tvMovieOverview.text = it.overview
                                tvMovieDateRelease.text = it.releaseDate
                                tvMovieRating.text = it.voteAverage.toString()
                                tvMovieRevenue.text = it.revenue.toString()
                                tvMovieRuntime.text = it.runtime.toString()
                                tvMovieTagLine.text = it.tagline
                                tvMovieTitle.text = it.title

                                imgMovie.load(moviePoster) {
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }

                                imgMovieBack.load(moviePoster) {
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }

                            }
                        }
                        401 -> {
                            Toast.makeText(requireContext(), "Invalid API key: You must be granted a valid key.", Toast.LENGTH_SHORT).show()
                        }
                        404 -> {
                            Toast.makeText(requireContext(), "The resource you requested could not be found.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }



}