package com.devkominfosda.moviecataloguebajp1.di

import com.devkominfosda.moviecataloguebajp1.core.domain.usecase.MoviesInteractor
import com.devkominfosda.moviecataloguebajp1.core.domain.usecase.MoviesUseCase
import com.devkominfosda.moviecataloguebajp1.ui.details.DetailsViewModel
import com.devkominfosda.moviecataloguebajp1.ui.home.HomeViewModel
import com.devkominfosda.moviecataloguebajp1.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MoviesUseCase> { MoviesInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}