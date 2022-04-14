package com.dicoding.capstoneproject.detail

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.capstoneproject.core.BuildConfig
import com.dicoding.capstoneproject.core.data.wrapper.ResourceStatus
import com.dicoding.capstoneproject.core.domain.model.MediaModel
import com.dicoding.capstoneproject.core.utility.EspressoIdlingResource
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModel()
    private var favoriteMenuItem: MenuItem? = null

    private var mediaModel: MediaModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extraMedia = intent.getParcelableExtra<MediaModel>(EXTRA_MEDIA)

        if (extraMedia != null) {

            viewModel.mediaDetail(extraMedia.mediaType!!, extraMedia.id.toString()).observe(this, { media ->
                if (media == null)
                    return@observe

                when (media.status) {
                    ResourceStatus.SUCCESS -> {
                        binding.progressBarDetail.visibility = View.GONE
                        media.data?.let {
                            mediaModel = it
                            Log.e("DETAIL", mediaModel!!.favorite.toString())

                            binding.layoutContent.textTitle.text = it.name
                            binding.layoutContent.textDate.text = it.firstReleasedDate

                            binding.layoutContent.textDesc.text = it.description

                            Glide.with(this)
                                .load(BuildConfig.POSTER_URL + it.imagePath)
                                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                                .error(R.drawable.ic_error)
                                .into(binding.layoutContent.imagePoster)

                            binding.layoutContent.contentParent.visibility = View.VISIBLE

                            setFavoriteState(it.favorite)

                            if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                                EspressoIdlingResource.decrement()
                            }
                        }
                    }

                    ResourceStatus.LOADING -> {
                        binding.progressBarDetail.visibility = View.VISIBLE
                    }

                    ResourceStatus.ERROR -> {
                        binding.tvErrorDetail.visibility = View.GONE
                        binding.tvErrorDetail.visibility = View.VISIBLE
                        binding.tvErrorDetail.text = media.message
                    }
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        favoriteMenuItem = menu.findItem(R.id.favorites_detail)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorites_detail -> {
                mediaModel?.let {
                    viewModel.setFavoriteState(it, !it.favorite)
                    setFavoriteState(!it.favorite)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onNavigateUp()
    }

    private fun setFavoriteState(state: Boolean) {

        favoriteMenuItem?.let { item ->
            item.isChecked = state
            item.icon =
                if (!state) ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_baseline_favorite_border_24,
                    null
                )
                else ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_baseline_favorite_24_full,
                    null
                )
        }
    }

    companion object {
        const val EXTRA_MEDIA = "extra_media"
    }
}