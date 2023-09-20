package com.dicoding.firstgithubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.firstgithubuser.data.room.UserEntity

class UserDiffCallback(
    private val oldList: List<UserEntity>,
    private val newList: List<UserEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList == newList

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldList[oldItemPosition].username
        val newNote = newList[newItemPosition].username
        return oldNote == newNote
    }

}