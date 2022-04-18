package com.dicoding.capstoneproject.di

import com.dicoding.capstoneproject.core.domain.usecase.IMediaUseCase
import com.dicoding.capstoneproject.core.domain.usecase.MediaInteractor
import com.dicoding.capstoneproject.detail.DetailViewModel
import com.dicoding.capstoneproject.media.MediaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MediaViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

val useCaseModule = module {
    factory<IMediaUseCase> { MediaInteractor(get()) }
}