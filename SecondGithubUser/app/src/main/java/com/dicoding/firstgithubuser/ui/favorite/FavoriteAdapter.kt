package com.dicoding.firstgithubuser.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.firstgithubuser.R
import com.dicoding.firstgithubuser.data.room.UserEntity
import com.dicoding.firstgithubuser.databinding.ItemListFavoriteBinding
import com.dicoding.firstgithubuser.helper.UserDiffCallback
import com.dicoding.firstgithubuser.ui.detail.DetailActivity

class FavoriteAdapter(private val favoriteVM: FavoriteViewModel) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var listFavorites = emptyList<UserEntity>()
    fun updateFavoriteLists(listFav: List<UserEntity>) {
        val diffCallback = UserDiffCallback(listFavorites, listFav)
        val difResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites = listFav
        difResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemListFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity) {
            binding.apply {
                tvItemUsername.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(
                        RequestOptions
                            .circleCropTransform()
                            .placeholder(R.drawable.github)
                            .error(R.drawable.github)
                    ).into(ivItemAvatar)
            }

            itemView.setOnClickListener {
                val intentToDetail = Intent(itemView.context, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                itemView.context.startActivity(intentToDetail)
            }

            binding.ivFavorited.setOnClickListener {
                favoriteVM.deleteListFav(user.username)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listFavorites.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userPosition = listFavorites[position]
        holder.bind(userPosition)

    }
}