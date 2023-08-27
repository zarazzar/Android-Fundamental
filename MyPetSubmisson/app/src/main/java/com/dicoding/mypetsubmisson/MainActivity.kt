package com.dicoding.mypetsubmisson

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mypetsubmisson.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<PetStore>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvPetstore.setHasFixedSize(true)
        list.addAll(getListStores())
        showRecycleList()

    }

    private fun getListStores(): ArrayList<PetStore> {
        val dataTitle = resources.getStringArray(R.array.data_title)
        val dataOpenTime = resources.getStringArray(R.array.data_openTime)
        val dataAddress = resources.getStringArray(R.array.data_address)
        val dataPhoneNumber = resources.getStringArray(R.array.data_phoneNumber)
        val dataPhotos = resources.getStringArray(R.array.data_photo)
        val dataDescription = resources.getStringArray(R.array.data_description)

        val listPetStore = ArrayList<PetStore>()
        for (i in dataTitle.indices) {
            val petstore = PetStore(dataTitle[i],dataOpenTime[i],dataAddress[i], dataPhoneNumber[i], dataPhotos[i],dataDescription[i])
            listPetStore.add(petstore)
        }
        return listPetStore
    }

    private fun showRecycleList() {
        binding.rvPetstore.layoutManager = LinearLayoutManager(this)
        val listStoreAdapter = ListStoreAdapter(list)
        binding.rvPetstore.adapter = listStoreAdapter

        listStoreAdapter.setOnItemClickCallback(object : ListStoreAdapter.OnItemClickCallback{
            override fun onItemClicked(data: PetStore) {
                val intentToDetail = Intent(this@MainActivity,DetailActivity::class.java)
                intentToDetail.putExtra("DATA",data)
                startActivity(intentToDetail)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.about_page, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.about_me -> {
                val intentToAbout = Intent (this@MainActivity,AboutActivity::class.java)
                startActivity(intentToAbout)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}