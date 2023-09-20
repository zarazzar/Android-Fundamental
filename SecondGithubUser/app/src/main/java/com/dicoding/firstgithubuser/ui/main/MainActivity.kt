package com.dicoding.firstgithubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.dicoding.firstgithubuser.R
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.firstgithubuser.databinding.ActivityMainBinding
import com.dicoding.firstgithubuser.data.response.AllUsersItem
import com.dicoding.firstgithubuser.helper.ViewModelFactory
import com.dicoding.firstgithubuser.ui.favorite.FavoriteActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        factory = ViewModelFactory.getInstance(this@MainActivity)

        showUserLists()

        //observe LiveData
        mainViewModel.dataUser.observe(this) { dataUser ->
            setDataUsers(dataUser!!)
        }

        mainViewModel.snacbarText.observe(this) {
            it.getContentIfNotHandled().let { snackBarText ->
                Snackbar.make(window.decorView.rootView, snackBarText!!, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        mainViewModel.isLoading.observe(this) {
            showProgressbar(it)
        }

        //searchbar
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText
                .setOnEditorActionListener { textView, actionId, event ->
                    val queryView = searchView.text.toString().trim()
                    searchBar.text = queryView
                    searchView.hide()
                    if (queryView.isEmpty()) {
                        mainViewModel.showAllUser()
                    } else {
                        mainViewModel.searchUser(queryView)
                    }
                    true
                }
        }

        //getmode
        mainViewModel.getmode().observe(this) { isDarkModeActive: Boolean ->
            isDarkMode = isDarkModeActive
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    private fun showUserLists() {
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }

    private fun setDataUsers(allUsers: List<AllUsersItem>) {
        val adapter = UserAdapter(allUsers)
        binding.rvUsers.adapter = adapter
    }

    private fun showProgressbar(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    //save mode
    private fun saveMode() {
        val modes = arrayOf("Dark", "Light")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Mode")
        builder.setItems(modes) { _, mode ->
            mainViewModel.saveThemeSetting(mode == 0)
        }
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val modeIcon = menu.findItem(R.id.modeIcon)
        mainViewModel.getmode().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) modeIcon.setIcon(R.drawable.ic_lightmode) else modeIcon.setIcon(R.drawable.ic_darkmode)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favIcon -> {
                val intentToFavorite = Intent(this, FavoriteActivity::class.java)
                startActivity(intentToFavorite)
                true
            }

            R.id.modeIcon -> {
                saveMode()
                true
            }

            else -> true
        }
    }

    companion object {
        var isDarkMode: Boolean = false
    }
}