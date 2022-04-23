package com.dicoding.capstoneproject.media

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.capstoneproject.core.BuildConfig
import com.dicoding.capstoneproject.core.R
import com.dicoding.capstoneproject.core.databinding.ItemsMediaBinding
import com.dicoding.capstoneproject.core.domain.model.MediaModel
import com.dicoding.capstoneproject.detail.DetailActivity

class MediaListAdapter: RecyclerView.Adapter<MediaListAdapter.MediaViewHolder>(){

    private val mediaList = mutableListOf<MediaModel>()

    fun submitList(newList: List<MediaModel>) {
        if(newList.isEmpty())
            return

        mediaList.clear()
        mediaList.addAll(newList)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder =
        MediaViewHolder(ItemsMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val media = mediaList[position]
        holder.bind(media)
    }

    override fun getItemCount(): Int = mediaList.size

    inner class MediaViewHolder(private val binding: ItemsMediaBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(media: MediaModel) {

            with(binding) {

                tvTitle.text = media.name
                tvReleaseDate.text = media.firstReleasedDate

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_MEDIA, media)
                    itemView.context.startActivity(intent)
                }

                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_URL + media.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imgPoster)
            }
        }
    }

}