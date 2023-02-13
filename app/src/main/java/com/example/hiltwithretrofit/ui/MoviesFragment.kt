package com.example.hiltwithretrofit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.hiltwithretrofit.R
import com.example.hiltwithretrofit.adapter.LoadMoreAdapter
import com.example.hiltwithretrofit.adapter.MoviesAdapter
import com.example.hiltwithretrofit.databinding.FragmentMoviesBinding
import com.example.hiltwithretrofit.repository.ApiRepository
import com.example.hiltwithretrofit.response.MoviesListResponse
import com.example.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding: FragmentMoviesBinding
    get() = _binding!!

    private val viewModel: MoviesViewModel by viewModels()

    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleScope.launchWhenCreated {
                viewModel.movieList.collect {
                    moviesAdapter.submitData(it)
                }
            }

            rlMovies.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = moviesAdapter
            }

            lifecycleScope.launchWhenCreated {
                moviesAdapter.loadStateFlow.collect {
                    val state = it.refresh
                    prgBarMovies.isVisible = state is LoadState.Loading
                }
            }

            rlMovies.adapter = moviesAdapter.withLoadStateFooter(
                LoadMoreAdapter {
                    moviesAdapter.retry()
                }
            )

            moviesAdapter.setOnItemClickListener {
                val direction = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it.id)
                findNavController().navigate(direction)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}