package com.dicoding.capstoneproject.core.utility

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"
    private val countingIdlingResource = CountingIdlingResource(RESOURCE)
    private val movieCountingIdlingResource = CountingIdlingResource("MOVIE")

    val idlingResource: IdlingResource
        get() = countingIdlingResource

    val movieIdlingResource: IdlingResource
        get() = movieCountingIdlingResource

    fun increment(){
        countingIdlingResource.increment()
    }

    fun decrement(){
        countingIdlingResource.decrement()
    }

    fun incrementMovie(){
        movieCountingIdlingResource.increment()
    }

    fun decrementMovie() {
        movieCountingIdlingResource.decrement()
    }
}