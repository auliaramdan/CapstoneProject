package com.dicoding.capstone.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.capstoneproject.core.domain.usecase.IMediaUseCase

class FavoriteViewModel (repository: IMediaUseCase): ViewModel() {
    val favoriteMediaList = repository.getMediaList(true).asLiveData()

}