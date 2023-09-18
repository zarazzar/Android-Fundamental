package com.dicoding.firstgithubuser.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.firstgithubuser.R
import com.dicoding.firstgithubuser.databinding.FragmentFollowingBinding
import com.dicoding.firstgithubuser.response.AllUsersItem
import com.dicoding.firstgithubuser.ui.detail.DetailAdapter
import com.dicoding.firstgithubuser.ui.detail.TabsViewModel


class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowingBinding.bind(view)

        val position = arguments?.getInt(ARG_POSITION, 0)
        val username = arguments?.getString(ARG_USERNAME)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollows.layoutManager = layoutManager


        val tabsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[TabsViewModel::class.java]
        // observe viewModel
        tabsViewModel.listUserFollow.observe(viewLifecycleOwner) { listUser ->
            setDataFollow(listUser)
        }

        tabsViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        tabsViewModel.isZero.observe(viewLifecycleOwner) {
            showZeroFollow(it)
        }

        tabsViewModel.snackbarText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled().let { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }

        if (username != null && savedInstanceState == null) {
            tabsViewModel.getFollow(username, position == 1)
        }

    }

    private fun setDataFollow(followList: List<AllUsersItem>) {
        val adapter = DetailAdapter(followList)
        binding.rvFollows.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showZeroFollow(isZero: Boolean) {
        if (isZero) {
            binding.tvIsZero.visibility = View.VISIBLE
        } else {
            binding.tvIsZero.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val ARG_POSITION = "ARG_POSITION"
        val ARG_USERNAME: String? = "ARG_USERNAME"
    }

}