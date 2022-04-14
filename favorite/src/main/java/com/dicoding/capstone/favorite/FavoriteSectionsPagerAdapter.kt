package com.dicoding.capstone.favorite

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.capstoneproject.core.domain.model.MediaType
import com.dicoding.capstoneproject.media.MediaFragment

class FavoriteSectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = TAB_TITLES.size

    override fun getItem(position: Int): Fragment = FavoriteMediaFragment(MediaType.values()[position])

    override fun getPageTitle(position: Int): CharSequence = MediaType.values()[position].modelTypeName

    companion object {
        private val TAB_TITLES = MediaType.values()
    }
}