package com.dicoding.capstoneproject.core.data

import com.dicoding.capstoneproject.core.data.local.LocalDataSource
import com.dicoding.capstoneproject.core.data.local.entity.MediaItem
import com.dicoding.capstoneproject.core.data.remote.ApiResponse
import com.dicoding.capstoneproject.core.data.remote.RemoteDataSource
import com.dicoding.capstoneproject.core.data.wrapper.ResourceWrapper
import com.dicoding.capstoneproject.core.domain.model.MediaModel
import com.dicoding.capstoneproject.core.domain.model.MediaType
import com.dicoding.capstoneproject.core.domain.repository.IMediaRepository
import com.dicoding.capstoneproject.core.utility.DataMapper
import com.dicoding.capstoneproject.core.utility.ExecutorsPool
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val executorsPool: ExecutorsPool
): IMediaRepository {

    override fun getMediaList(requireFavorite: Boolean): Flow<ResourceWrapper<List<MediaModel>>> {
        return object: NetworkResourceHandler<List<MediaModel>, List<MediaItem>>() {
            override fun fetchFromDB(): Flow<List<MediaModel>> {
                return if(!requireFavorite)
                    localDataSource.getMediaList().map {
                        DataMapper.mapEntityListToDomainList(it)
                    }
                else
                    localDataSource.getFavoriteMediaList().map {
                        DataMapper.mapEntityListToDomainList(it)
                    }
            }

            override fun shouldFetch(data: List<MediaModel>?): Boolean =
                data == null || data.isEmpty()


            override suspend fun startFetch(): Flow<ApiResponse<List<MediaItem>>> {
                return remoteDataSource.getMediaList()
            }

            override suspend fun saveNetworkResult(data: List<MediaItem>) {
                localDataSource.insertMediaList(data)
            }

        }.asFlow()
    }

    override fun getMediaDetail(type: MediaType, id: String): Flow<ResourceWrapper<MediaModel>> {
        return object : NetworkResourceHandler<MediaModel, MediaItem>() {
            override fun fetchFromDB(): Flow<MediaModel> {
                return localDataSource.getMediaDetail(type.entityTypeName, id).map { DataMapper.mapSingleEntityToSingleDomain(it) }
            }

            override fun shouldFetch(data: MediaModel?): Boolean {
                return data == null
            }

            override suspend fun startFetch(): Flow<ApiResponse<MediaItem>> {
                return remoteDataSource.getMediaDetail(type.entityTypeName, id)
            }

            override suspend fun saveNetworkResult(data: MediaItem) {
                localDataSource.insertMediaItem(data)
            }

        }.asFlow()
    }

    override fun setFavoriteState(model: MediaModel, state: Boolean) {
        executorsPool.diskIO().execute { localDataSource.setFavoriteState(DataMapper.mapSingleDomainToSingleEntity(model), state) }
    }
}