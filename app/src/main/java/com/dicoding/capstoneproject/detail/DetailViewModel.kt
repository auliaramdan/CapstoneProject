package com.dicoding.capstoneproject.detail

import androidx.lifecycle.*
import com.dicoding.capstoneproject.core.data.wrapper.ResourceWrapper
import com.dicoding.capstoneproject.core.domain.model.MediaModel
import com.dicoding.capstoneproject.core.domain.model.MediaType
import com.dicoding.capstoneproject.core.domain.usecase.IMediaUseCase

class DetailViewModel (private val repository: IMediaUseCase) : ViewModel() {

    fun mediaDetail(type: MediaType, id: String): LiveData<ResourceWrapper<MediaModel>> {
        return repository.getMediaDetail(type, id).asLiveData()
    }

    fun setFavoriteState(media: MediaModel, state: Boolean) =  repository.setFavoriteState(media, state)
}