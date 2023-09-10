package com.dicoding.firstgithubuser.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.firstgithubuser.R
import com.dicoding.firstgithubuser.databinding.ItemListBinding
import com.dicoding.firstgithubuser.response.AllUsersItem
import com.dicoding.firstgithubuser.ui.detail.DetailActivity

class UserAdapter (private val githubUser: List<AllUsersItem>) : RecyclerView.Adapter<UserAdapter.MainViewHolder>(){

    class MainViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: AllUsersItem){
            binding.tvItemUsername.text = users.login
            Glide.with(itemView.context)
                .load(users.avatarUrl)
                .apply(RequestOptions.circleCropTransform()
                    .placeholder(R.drawable.github)
                    .error(R.drawable.github))
                .into(binding.ivItemAvatar)

            itemView.setOnClickListener{
                val intentToDetail = Intent(itemView.context, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, users.login)
                itemView.context.startActivity(intentToDetail)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return githubUser.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val users = githubUser[position]
        holder.bind(users)
    }
}