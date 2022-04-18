package com.dicoding.capstone.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.capstone.favorite.databinding.ActivityFavoriteBinding
import com.dicoding.capstoneproject.common.SectionsPagerAdapter
import com.example.capstoneproject.databinding.ActivityMainBinding
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private var _binding : ActivityFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

        val sectionsPagerAdapter = FavoriteSectionsPagerAdapter(supportFragmentManager)
        binding.viewPagerFavorite.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPagerFavorite)

        supportActionBar?.title = "Favorites"
        supportActionBar?.elevation = 0f
    }
}