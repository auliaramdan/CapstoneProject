package com.dicoding.capstoneproject.media

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
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MediaViewModelTest : KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @OptIn(ObsoleteCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var viewModel: MediaViewModel

    @Mock
    private lateinit var useCase: MediaInteractor

    private val dummyMediaList = listOf(MediaModel(id = 0, name = "Test movie", mediaType = MediaType.MOVIE), MediaModel(id = 1, name = "Test show", mediaType = MediaType.TV_SHOW))

    @Before
    fun before() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = MediaViewModel(useCase)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `test get list`() = runBlocking {

        Mockito.`when`(useCase.getMediaList(false)).thenReturn(flow {
            emit(ResourceWrapper.success(dummyMediaList))
        })

        assertNotNull(viewModel.mediaList().getLiveDataValue())

        assertEquals(0, viewModel.mediaList().getLiveDataValue().data?.get(0)?.id)
        assertEquals("Test movie", viewModel.mediaList().getLiveDataValue().data?.get(0)?.name)
        assertEquals(MediaType.MOVIE, viewModel.mediaList().getLiveDataValue().data?.get(0)?.mediaType)

        assertEquals(1, viewModel.mediaList().getLiveDataValue().data?.get(1)?.id)
        assertEquals("Test show", viewModel.mediaList().getLiveDataValue().data?.get(1)?.name)
        assertEquals(MediaType.TV_SHOW, viewModel.mediaList().getLiveDataValue().data?.get(1)?.mediaType)
    }
}