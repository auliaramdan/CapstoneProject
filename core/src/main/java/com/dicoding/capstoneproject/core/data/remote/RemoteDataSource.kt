package com.dicoding.capstoneproject.core.data.remote

import android.util.Log
import com.dicoding.capstoneproject.core.data.local.entity.MediaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(val apiService: ApiService) {

    suspend fun getMediaList() : Flow<ApiResponse<List<MediaItem>>> {
        return flow {
            try {
                val response = apiService.getMediaList("8173109")
                val dataArray = response.items

                dataArray?.let {
                    if(dataArray.isNotEmpty()) {
                        emit(ApiResponse.Success(response.items))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("REMOTE", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMediaDetail(type: String, id: String) : Flow<ApiResponse<MediaItem>> {

        return flow {
            try {
                val response = apiService.getMediaDetail(type, id)

                if(response.name != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("REMOTE", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}