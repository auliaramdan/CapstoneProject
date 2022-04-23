package com.dicoding.capstoneproject.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.capstoneproject.core.data.wrapper.ResourceWrapper
import com.dicoding.capstoneproject.core.domain.model.MediaModel
import com.dicoding.capstoneproject.core.domain.model.MediaType
import com.dicoding.capstoneproject.core.domain.usecase.MediaInteractor
import com.dicoding.capstoneproject.utils.getLiveDataValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @OptIn(ObsoleteCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var viewModel: DetailViewModel

    @Mock
    private lateinit var useCase: MediaInteractor

    @Before
    fun before() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = DetailViewModel(useCase)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `test get detail`() = runBlocking {

        Mockito.`when`(useCase.getMediaDetail(MediaType.MOVIE, "0")).thenReturn(flow {
            emit(ResourceWrapper.success(MediaModel(0, "Test", "", "", "", false, MediaType.MOVIE)))
        })

        assertNotNull(viewModel.mediaDetail(MediaType.MOVIE, "0").getLiveDataValue())
        assertEquals(0, viewModel.mediaDetail(MediaType.MOVIE, "0").getLiveDataValue().data?.id)
        assertEquals("Test", viewModel.mediaDetail(MediaType.MOVIE, "0").getLiveDataValue().data?.name)
        assertEquals(MediaType.MOVIE, viewModel.mediaDetail(MediaType.MOVIE, "0").getLiveDataValue().data?.mediaType)
    }
}