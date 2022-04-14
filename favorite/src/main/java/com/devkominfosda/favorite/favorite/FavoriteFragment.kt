package com.devkominfosda.favorite.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devkominfosda.favorite.databinding.FragmentFavoriteBinding
import com.devkominfosda.favorite.di.favoriteModule
import com.devkominfosda.moviecataloguebajp1.core.domain.model.Movies
import com.devkominfosda.moviecataloguebajp1.core.ui.MoviesAdapter
import com.devkominfosda.moviecataloguebajp1.ui.details.DetailsActivity
import com.devkominfosda.moviecataloguebajp1.core.utils.gone
import com.devkominfosda.moviecataloguebajp1.core.utils.startActivity
import com.devkominfosda.moviecataloguebajp1.core.utils.visible
import org.koin.core.context.loadKoinModules
import org.koin.android.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {

    private var _favoriteBinding: FragmentFavoriteBinding? = null
    private val binding get() = _favoriteBinding

    private val movieAdapter: MoviesAdapter by lazy { MoviesAdapter() }
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _favoriteBinding = FragmentFavoriteBinding.inflate(
            layoutInflater,
            container, false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        binding?.progressSpinKitList?.visible()
        viewModel.getFavoriteMovies().observe(viewLifecycleOwner) { movies ->
            movieAdapter.setData(movies)
            showDataFavorite(movies)
        }

        movieAdapter.onItemClick = { selectedData ->
            requireActivity().startActivity<DetailsActivity>(
                DetailsActivity.EXTRA_DATA to selectedData
            )
        }

        with(binding?.rvMovieFavorite) {
            this?.setHasFixedSize(true)
            this?.adapter = movieAdapter
        }
    }

    private fun showDataFavorite(movies: List<Movies>) {
        if (movies.isEmpty()) {
            binding?.apply {
                progressSpinKitList.gone()
                emptyLayout.visible()
                rvMovieFavorite.gone()
            }
        } else {
            binding?.apply {
                progressSpinKitList.gone()
                emptyLayout.gone()
                rvMovieFavorite.visible()
            }
        }
    }
}