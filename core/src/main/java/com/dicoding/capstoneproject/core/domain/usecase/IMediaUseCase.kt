package com.dicoding.capstoneproject.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.dicoding.capstoneproject.core.data.local.entity.MediaItem
import com.dicoding.capstoneproject.core.data.wrapper.ResourceWrapper
import com.dicoding.capstoneproject.core.domain.model.MediaModel
import com.dicoding.capstoneproject.core.domain.model.MediaType
import kotlinx.coroutines.flow.Flow

interface IMediaUseCase {
    fun getMediaList(requireFavorite: Boolean): Flow<ResourceWrapper<List<MediaModel>>>

    fun getMediaDetail(type: MediaType, id: String): Flow<ResourceWrapper<MediaModel>>

    fun setFavoriteState(media: MediaModel, state: Boolean)
}