package com.dicoding.firstgithubuser.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.firstgithubuser.databinding.ActivityFavoriteBinding
import com.dicoding.firstgithubuser.helper.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private lateinit var favoriteAdapter: FavoriteAdapter

    private lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels { factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        favoriteAdapter = FavoriteAdapter(favoriteViewModel)

        showFavoriteLists()

        favoriteViewModel.getFavUser().observe(this) { favorited ->
            binding.progressBar.visibility = View.GONE
            favoriteAdapter.updateFavoriteLists(favorited)

            val emptyList = favorited.isEmpty()
            if (emptyList) {
                emptyText(emptyList)
            } else {
                emptyText(emptyList)
            }
        }

    }

    private fun showFavoriteLists() {
        binding.rvFavorited.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }
    }

    private fun emptyText(isEmpty: Boolean) {
        binding.tvNofavorited.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}