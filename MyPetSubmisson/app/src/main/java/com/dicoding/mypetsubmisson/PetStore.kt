package com.dicoding.mypetsubmisson

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PetStore(
    val title: String,
    val openTime: String,
    val address: String,
    val phoneNumber: String,
    val photo: String,
    val description: String
) : Parcelable