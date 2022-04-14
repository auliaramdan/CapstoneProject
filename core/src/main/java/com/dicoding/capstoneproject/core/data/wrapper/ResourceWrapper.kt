package com.dicoding.capstoneproject.core.data.wrapper

class ResourceWrapper<T>(val status: ResourceStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ResourceWrapper<T> = ResourceWrapper(ResourceStatus.SUCCESS, data, null)

        fun <T> error(message: String?, data: T?): ResourceWrapper<T> = ResourceWrapper(
            ResourceStatus.ERROR, data, message)

        fun <T> loading(data: T? = null): ResourceWrapper<T> = ResourceWrapper(ResourceStatus.LOADING, data, null)
    }
}