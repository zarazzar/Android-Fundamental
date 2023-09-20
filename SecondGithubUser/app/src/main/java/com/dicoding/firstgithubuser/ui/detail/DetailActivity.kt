package com.dicoding.firstgithubuser.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.firstgithubuser.BuildConfig
import com.dicoding.firstgithubuser.R
import com.dicoding.firstgithubuser.databinding.ActivityDetailBinding
import com.dicoding.firstgithubuser.data.response.DetailUser
import com.dicoding.firstgithubuser.data.room.UserEntity
import com.dicoding.firstgithubuser.helper.ViewModelFactory
import com.dicoding.firstgithubuser.ui.favorite.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels { factory }
    private var avatar: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        detailViewModel.getUserDetail(username!!)

        detailViewModel.theDetails.observe(this) { detailUser ->
            setUserDetails(detailUser)
            this.avatar = detailUser.avatarUrl.toString()
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled().let { snackBarText ->
                Snackbar.make(window.decorView.rootView, snackBarText!!, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = intent.getStringExtra(EXTRA_USERNAME).toString()
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()
        supportActionBar?.elevation = 0f

        //favorite
        favoriteViewModel.getFavUser().observe(this) {favorite ->
            val isFavorited = favorite.any {
                it.username == username
            }
            setIconFab(isFavorited)
            binding.fabFavorite.setOnClickListener {
                if (!isFavorited) {
                    Toast.makeText(this@DetailActivity, "$username has been Added to favorite lists", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DetailActivity, "$username has been Removed from favorite lists", Toast.LENGTH_SHORT).show()
                }


                val table = username.let { UserEntity(it, avatar ,false) }

                try {
                    favoriteViewModel.addOrDeleteFavUser(table,favorite.any {
                        it.username == username
                    })
                } catch (e : Exception) {
                    Toast.makeText(this@DetailActivity, "Terjadi Kesalahan ", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    private fun setIconFab(isFavorited: Boolean) {
        binding.fabFavorite.apply {
            if (isFavorited){
                setImageDrawable(ContextCompat.getDrawable(this@DetailActivity,R.drawable.ic_favouritefilled))
            } else {
                setImageDrawable(ContextCompat.getDrawable(this@DetailActivity,R.drawable.ic_favourite))

            }
        }
    }

    private fun setUserDetails(detailUser: DetailUser) {
        binding.apply {
            tvName.text = detailUser.name
            tvUsername.text = detailUser.login
            tvFollowing.text = detailUser.following.toString()
            tvFollowers.text = detailUser.followers.toString()
            Glide.with(this@DetailActivity)
                .load(detailUser.avatarUrl)
                .apply(
                    RequestOptions.circleCropTransform()
                        .placeholder(R.drawable.github)
                        .error(R.drawable.github)
                )
                .into(ivAvatar)
        }

        supportActionBar?.apply {
            title = detailUser.name
            setDisplayHomeAsUpEnabled(true)
        }

        userShare = detailUser.login.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setShare(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setShare(selectedUser: Int) {
        when (selectedUser) {
            R.id.share_user -> {
                val intent = Intent(Intent.ACTION_SEND)
                val shareUser =
                    "I Want to share this Github Profile! \n${BuildConfig.URL_PROFILE}$userShare"
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, shareUser)
                startActivity(Intent.createChooser(intent, "Share with..."))
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        private lateinit var userShare: String
        private val TAB_TITLES = arrayOf(
            "Following",
            "Followers"
        )
    }


}