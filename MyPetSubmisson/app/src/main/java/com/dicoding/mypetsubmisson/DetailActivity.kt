package com.dicoding.mypetsubmisson

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.dicoding.mypetsubmisson.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<PetStore>("DATA", PetStore::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<PetStore>("DATA")
        }

        if (data != null) {
            binding.tvTitle.text = data.title
            binding.tvOpenTime.text = " opens at ${data.openTime}"
            binding.tvAddress.text = data.address
            binding.btnCall.text = data.phoneNumber
            binding.tvDescription.text = data.description

            Glide.with(this@DetailActivity)
                .load(data.photo)
                .into(binding.ivPhoto)

            binding.btnCall.setOnClickListener {
                val dialPhoneIntent =
                    Intent(Intent.ACTION_DIAL, Uri.parse("tel:${data.phoneNumber}"))
                startActivity(dialPhoneIntent)
            }

            binding.actionShare.setOnClickListener {
                val addressData = "Lihat nih,, Rekomendasi Petshop dari saya. \n ${data.title} , ${data.address}"
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, addressData)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            binding.ibFavourite.setOnClickListener {
                Toast.makeText(this, "Anda Menyukai ${data.title} :D", Toast.LENGTH_SHORT).show()
            }

            title = data.title // change action bar title


        }

    }


}