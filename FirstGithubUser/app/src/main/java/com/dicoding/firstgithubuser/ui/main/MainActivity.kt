package com.dicoding.firstgithubuser.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.firstgithubuser.databinding.ActivityMainBinding
import com.dicoding.firstgithubuser.response.AllUsersItem
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

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

    }

    private fun setDataUsers(allUsers: List<AllUsersItem>) {
        val adapter = UserAdapter(allUsers)
        binding.rvUsers.adapter = adapter
    }

    private fun showProgressbar(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}