package com.momen.bostatask.ui.viewer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.momen.bostatask.R
import com.momen.bostatask.databinding.FragmentImageViewerBinding


class ImageViewerFragment : Fragment() {
    private var _binding: FragmentImageViewerBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<ImageViewerFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            zoomageView.load(args.photo.url) {
                placeholder(R.drawable.logo)
                crossfade(true)
            }
            fabShare.setOnClickListener { shareImageURL(args.photo.url) }
        }
    }

    private fun shareImageURL(imageUrl: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, imageUrl)
        }
        startActivity(Intent.createChooser(shareIntent, "Share Image URL"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}