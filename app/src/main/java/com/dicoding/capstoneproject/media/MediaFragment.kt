package com.dicoding.capstoneproject.media

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.capstoneproject.core.data.wrapper.ResourceStatus
import com.dicoding.capstoneproject.core.data.wrapper.ResourceWrapper
import com.dicoding.capstoneproject.core.domain.model.MediaModel
import com.dicoding.capstoneproject.core.domain.model.MediaType
import com.example.capstoneproject.databinding.FragmentMediaBinding

class MediaFragment(private val mediaType: MediaType) : Fragment() {

    private var _binding : FragmentMediaBinding? = null
    private val binding get() = _binding!!

    private  val viewModel : MediaViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null) {
            binding.progressBarMediaList.tag = mediaType
            binding.rvMediaList.tag = mediaType

            viewModel.mediaList.observe(viewLifecycleOwner, {
                prepareMedia(it)
            })
        }
    }

    private fun prepareMedia(media: ResourceWrapper<List<MediaModel>>) {

        when(media.status) {
            ResourceStatus.SUCCESS -> {
                binding.progressBarMediaList.visibility = View.GONE

                val listAdapter = MediaListAdapter()
                media.data?.let { list ->
                    listAdapter.submitList(list.filter { it.mediaType == mediaType })
                }

                binding.rvMediaList.apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = listAdapter
                    visibility = View.VISIBLE
                }
            }

            ResourceStatus.LOADING -> {
                binding.progressBarMediaList.visibility = View.VISIBLE
            }

            ResourceStatus.ERROR -> {
                binding.progressBarMediaList.visibility = View.GONE
                binding.tvErrorMediaList.visibility = View.VISIBLE
                binding.tvErrorMediaList.text = media.message
            }
        }

    }
}