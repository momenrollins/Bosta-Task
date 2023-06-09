package com.momen.bostatask.ui.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.momen.bostatask.R
import com.momen.bostatask.data.model.Photo
import com.momen.bostatask.databinding.ItemPhotoBinding

class PhotosAdapter(private val onClick: (Photo) -> Unit) :
    RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    private var photos: List<Photo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(photos: List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }


    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.apply {
                ivPhoto.load(photo.thumbnailUrl) {
                    placeholder(R.drawable.logo)
                    crossfade(true)
                }
                tvTitle.text = photo.title
                cardItem.setOnClickListener { onClick(photo) }
            }
        }
    }
}
