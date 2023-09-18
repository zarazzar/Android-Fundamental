package com.dicoding.firstgithubuser.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.firstgithubuser.R
import com.dicoding.firstgithubuser.databinding.ActivityDetailBinding
import com.dicoding.firstgithubuser.response.DetailUser
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private val detailViewModel by viewModels<DetailViewModel>()

    private lateinit var binding: ActivityDetailBinding


    private lateinit var userShare: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        detailViewModel.getUserDetail(username!!)

        detailViewModel.theDetails.observe(this) { detailUser ->
            setUserDetails(detailUser)
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
                    "I Want to share this Github Profile! \nhttps://github.com/$userShare"
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

        private val TAB_TITLES = arrayOf(
            "Following",
            "Followers"
        )
    }


}