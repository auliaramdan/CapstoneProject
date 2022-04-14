package com.dicoding.capstoneproject.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.dicoding.capstoneproject.core.data.local.entity.MediaItem
import com.dicoding.capstoneproject.core.data.wrapper.ResourceWrapper
import com.dicoding.capstoneproject.core.domain.model.MediaModel
import com.dicoding.capstoneproject.core.domain.model.MediaType
import com.dicoding.capstoneproject.core.domain.repository.IMediaRepository
import kotlinx.coroutines.flow.Flow

class MediaInteractor(private val repository: IMediaRepository): IMediaUseCase {
    override fun getMediaList(requireFavorite: Boolean) = repository.getMediaList(requireFavorite)

    override fun getMediaDetail(type: MediaType, id: String) = repository.getMediaDetail(type, id)

    override fun setFavoriteState(media: MediaModel, state: Boolean) = repository.setFavoriteState(media, state)

}