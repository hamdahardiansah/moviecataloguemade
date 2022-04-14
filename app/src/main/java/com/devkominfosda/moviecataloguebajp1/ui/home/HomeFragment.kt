package com.devkominfosda.moviecataloguebajp1.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.devkominfosda.moviecataloguebajp1.R
import com.devkominfosda.moviecataloguebajp1.core.data.source.Resource
import com.devkominfosda.moviecataloguebajp1.core.domain.model.Movies
import com.devkominfosda.moviecataloguebajp1.core.ui.MoviesAdapter
import com.devkominfosda.moviecataloguebajp1.core.utils.Sort
import com.devkominfosda.moviecataloguebajp1.databinding.FragmentHomeBinding
import com.devkominfosda.moviecataloguebajp1.ui.details.DetailsActivity
import com.devkominfosda.moviecataloguebajp1.core.utils.gone
import com.devkominfosda.moviecataloguebajp1.core.utils.showToastShort
import com.devkominfosda.moviecataloguebajp1.core.utils.startActivity
import com.devkominfosda.moviecataloguebajp1.core.utils.visible
import com.jakewharton.rxbinding2.view.clicks
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _fragmentHomeBinding: FragmentHomeBinding? = null
    private val binding get() = _fragmentHomeBinding

    private val viewModel: HomeViewModel by viewModel()
    private val moviesAdapter: MoviesAdapter by lazy { MoviesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _fragmentHomeBinding = FragmentHomeBinding.inflate(
            layoutInflater, container, false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setList(Sort.NEWEST)

        moviesAdapter.onItemClick = { selectedData ->
            requireActivity().startActivity<DetailsActivity>(
                DetailsActivity.EXTRA_DATA to selectedData
            )
        }

        binding?.progressSpinKitList?.visible()
        with(binding?.rvMovie) {
            this?.setHasFixedSize(true)
            this?.adapter = moviesAdapter
        }

        onClickFAB()
    }

    @SuppressLint("CheckResult")
    private fun onClickFAB() {
        binding?.apply {
            fabNewest.clicks().subscribe { setList(Sort.NEWEST) }
            fabOldest.clicks().subscribe { setList(Sort.OLDEST) }
            fabPopularity.clicks().subscribe { setList(Sort.POPULARITY) }
        }
    }

    private fun setList(sort: String) {
        viewModel.getMovies(sort).observe(viewLifecycleOwner, movieObserver)
    }

    private val movieObserver = Observer<Resource<List<Movies>>> { movies ->
        if (movies != null) {
            when (movies) {
                is Resource.Loading -> binding?.progressSpinKitList?.visible()
                is Resource.Success -> {
                    binding?.progressSpinKitList?.gone()
                    moviesAdapter.setData(movies.data)
                }
                is Resource.Error -> {
                    binding?.progressSpinKitList?.gone()
                    requireActivity().showToastShort(getString(R.string.something_is_wrong))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentHomeBinding = null
    }
}