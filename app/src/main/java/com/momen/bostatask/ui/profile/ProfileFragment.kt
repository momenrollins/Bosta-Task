package com.momen.bostatask.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.momen.bostatask.databinding.FragmentProfileBinding
import com.momen.bostatask.utils.GeneralStates
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var albumAdapter: ArrayAdapter<String>
    private val viewModel: ProfileViewModel by viewModels()
    private var isDataLoaded = false

    companion object {
        private const val USER_ID = 1
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
        observeData()
        observeFetchDataStatus()
        if (!isDataLoaded)
            viewModel.fetchData(USER_ID)
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.fetchData(USER_ID)
        }
    }

    private fun setupListView() {
        albumAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1)
        binding.listViewAlbums.adapter = albumAdapter
        binding.listViewAlbums.setOnItemClickListener { _, _, i, _ ->
            viewModel.albums.value?.get(i)?.let {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToAlbumFragment(
                        it
                    )
                )
            }
        }
    }

    private fun observeData() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.textViewName.text = user.name
            binding.textViewAddress.text = user.address.run { "$street, $suite, $city, $zipcode" }
        }

        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            val albumTitles = albums.map { it.title }
            albumAdapter.clear()
            albumAdapter.addAll(albumTitles)
        }
    }

    private fun observeFetchDataStatus() {
        viewModel.fetchDataStatus.observe(viewLifecycleOwner) { state ->
            binding.swipeToRefresh.isRefreshing = false
            when (state) {
                is GeneralStates.Success -> {
                    binding.textViewMyAlbums.visibility = View.VISIBLE
                    isDataLoaded = true
                }
                is GeneralStates.Loading -> {
                    binding.swipeToRefresh.isRefreshing = true
                }
                is GeneralStates.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
                is GeneralStates.NoConnect -> {
                    Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}