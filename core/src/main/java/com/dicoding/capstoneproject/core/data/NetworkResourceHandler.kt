package com.dicoding.capstoneproject.core.data

import com.dicoding.capstoneproject.core.data.remote.ApiResponse
import com.dicoding.capstoneproject.core.data.wrapper.ResourceWrapper
import kotlinx.coroutines.flow.*

abstract class NetworkResourceHandler<ResultType, ResponseType> {

    private var result: Flow<ResourceWrapper<ResultType>> = flow {
        emit(ResourceWrapper.loading())
        val dbSource = fetchFromDB().first()

        if (shouldFetch(dbSource)) {
            emit(ResourceWrapper.loading())
            when (val apiResponse = startFetch().first()) {
                is ApiResponse.Success -> {
                    saveNetworkResult(apiResponse.data)
                    emitAll(fetchFromDB().map { ResourceWrapper.success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(fetchFromDB().map { ResourceWrapper.success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(ResourceWrapper.error<ResultType>(apiResponse.errorMessage, null))
                }
            }
        } else {
            emitAll(fetchFromDB().map {
                ResourceWrapper.success(it)
            })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun fetchFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun startFetch(): Flow<ApiResponse<ResponseType>>

    protected abstract suspend fun saveNetworkResult(data: ResponseType)

    fun asFlow(): Flow<ResourceWrapper<ResultType>> = result
}