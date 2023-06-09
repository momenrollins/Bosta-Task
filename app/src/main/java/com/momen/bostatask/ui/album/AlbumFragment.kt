package com.momen.bostatask.ui.album

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.momen.bostatask.data.model.Photo
import com.momen.bostatask.databinding.FragmentAlbumBinding
import com.momen.bostatask.ui.profile.PhotosAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumFragment : Fragment() {
    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!
    private val args: AlbumFragmentArgs by navArgs()
    private val viewModel: AlbumViewModel by viewModels()
    private var originalList: List<Photo> = emptyList()
    private var currentSearchQuery: String = ""
    private lateinit var photosAdapter: PhotosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchPhotos(args.album.id)
        setupRecyclerView()
        observePhotos()
        setupSearchBar()
    }

    private fun setupRecyclerView() {
        photosAdapter = PhotosAdapter { photo ->
            findNavController().navigate(
                AlbumFragmentDirections.actionAlbumFragmentToImageViewerFragment(
                    photo
                )
            )
        }
        binding.rvPhotos.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = photosAdapter
        }
    }

    private fun observePhotos() {
        viewModel.photos.observe(viewLifecycleOwner) { photos ->
            originalList = photos
            if (currentSearchQuery.isNotEmpty()) {
                filterPhotos()
            } else {
                photosAdapter.submitList(photos)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading && originalList.isEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvPhotos.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rvPhotos.visibility = View.VISIBLE
            }
        }
    }

    private fun setupSearchBar() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentSearchQuery = s.toString().trim()
                filterPhotos()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun filterPhotos() {
        val filteredList =
            originalList.filter { it.title.contains(currentSearchQuery, ignoreCase = true) }
        photosAdapter.submitList(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}