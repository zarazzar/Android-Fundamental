package com.dicoding.firstgithubuser.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.firstgithubuser.R
import com.dicoding.firstgithubuser.databinding.ItemListBinding
import com.dicoding.firstgithubuser.data.response.AllUsersItem

class DetailAdapter(private val userFollows: List<AllUsersItem>) :
    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    class DetailViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userFollow: AllUsersItem) {
          binding.apply {
              tvItemUsername.text = userFollow.login
              Glide.with(itemView.context)
                  .load(userFollow.avatarUrl)
                  .apply(
                      RequestOptions.circleCropTransform()
                          .placeholder(R.drawable.github)
                          .error(R.drawable.github)
                  )
                  .into(ivItemAvatar)
          }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userFollows.size
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val follows = userFollows[position]
        holder.bind(follows)
    }
}