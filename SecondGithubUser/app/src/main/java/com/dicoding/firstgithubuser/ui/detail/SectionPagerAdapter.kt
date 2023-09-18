package com.dicoding.firstgithubuser.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.firstgithubuser.ui.fragment.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    var username: String = "-"
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowingFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowingFragment.ARG_POSITION, position + 1)
            putString(FollowingFragment.ARG_USERNAME, username)
        }
        return fragment
    }

}