package com.dicoding.capstone.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.capstone.favorite.databinding.ActivityFavoriteBinding
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