package com.dicoding.capstoneproject.media

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.capstoneproject.core.domain.usecase.IMediaUseCase

class MediaViewModel (repository: IMediaUseCase): ViewModel() {
    val mediaList = repository.getMediaList(false).asLiveData()

}