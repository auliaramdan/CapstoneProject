package com.dicoding.capstoneproject.detail

import com.dicoding.capstoneproject.core.domain.usecase.IMediaUseCase
import com.dicoding.capstoneproject.core.domain.usecase.MediaInteractor
import org.junit.Rule
import org.junit.Test
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class DetailViewModelTest : KoinTest {

    val testModule = module {
        single <IMediaUseCase> { MediaInteractor(get()) }
        viewModel { DetailViewModel(get()) }
    }

    val model by inject<DetailViewModel>()
    val repository by inject<IMediaUseCase>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(testModule)
    }

    @Test
    fun `model test`() {
        //model.mediaDetail()
    }
}