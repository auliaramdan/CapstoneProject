package com.dicoding.capstoneproject.common

import android.content.Context
import android.os.SystemClock
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.ViewPagerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dicoding.capstoneproject.core.data.local.room.MediaDao
import com.dicoding.capstoneproject.core.data.local.room.MediaDao_Impl
import com.dicoding.capstoneproject.core.data.local.room.MediaDatabase
import com.dicoding.capstoneproject.core.di.databaseModule
import com.dicoding.capstoneproject.core.domain.model.MediaType
import com.dicoding.capstoneproject.core.utility.EspressoIdlingResource
import com.example.capstoneproject.R
import kotlinx.coroutines.flow.first
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get

class MainActivityTest {


    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        //get<MediaDao_Impl>(MediaDao::class.java).deleteAll()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.movieIdlingResource)
    }

    @Test
    fun checkMovieRecyclerView(){
        EspressoIdlingResource.incrementMovie()
        Espresso.onView(
            Matchers.allOf(
                withId(R.id.progress_bar_media_list),
                ViewMatchers.withTagValue(`is`(MediaType.MOVIE))
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        Espresso.onView(
            Matchers.allOf(
                withId(R.id.rv_media_list),
                ViewMatchers.isCompletelyDisplayed()
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun checkShowRecyclerView(){
        EspressoIdlingResource.increment()
        Espresso.onView(withId(R.id.view_pager)).perform(ViewPagerActions.scrollRight())
        SystemClock.sleep(1000)
        Espresso.onView(
            Matchers.allOf(
                withId(R.id.progress_bar_media_list),
                ViewMatchers.withTagValue(`is`(MediaType.TV_SHOW))
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        Espresso.onView(
            Matchers.allOf(
                withId(R.id.rv_media_list),
                ViewMatchers.isCompletelyDisplayed()
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun checkMovieDetail(){
        EspressoIdlingResource.incrementMovie()
        Espresso.onView(
            Matchers.allOf(
                withId(R.id.rv_media_list),
                ViewMatchers.withTagValue(`is`(MediaType.MOVIE))
            )
        ).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            ViewActions.click()
        ))
        Espresso.onView(withId(R.id.text_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.image_poster))
        Espresso.onView(withId(R.id.progress_bar_detail))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun checkShowDetail(){
        EspressoIdlingResource.increment()
        Espresso.onView(withId(R.id.view_pager)).perform(ViewPagerActions.scrollRight())
        SystemClock.sleep(1000)
        Espresso.onView(
            Matchers.allOf(
                withId(R.id.rv_media_list),
                ViewMatchers.withTagValue(`is`(MediaType.TV_SHOW))
            )
        ).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            ViewActions.click()
        ))
        Espresso.onView(withId(R.id.text_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.image_poster))
        Espresso.onView(withId(R.id.progress_bar_detail))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.movieIdlingResource)
    }
}