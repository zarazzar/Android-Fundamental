package com.dicoding.myrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.myrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<Hero>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rvHeroes.setHasFixedSize(true)

        list.addAll(getListHeroes())
        showRecyclerList()
    }

    private fun getListHeroes(): ArrayList<Hero> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.getStringArray(R.array.data_photo)

        val listHero = ArrayList<Hero>()
        for (i in dataName.indices) {
            val hero = Hero(dataName[i], dataDescription[i], dataPhoto[i])
            listHero.add(hero)
        }
        return listHero
    }

    private fun showRecyclerList() {
        binding.rvHeroes.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListHeroAdapter(list)
        binding.rvHeroes.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : ListHeroAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hero) {
                showSelectedHero(data)
            }
        })
    }

    private fun showSelectedHero(hero: Hero) {
        Toast.makeText(this, "Kamu memilih " + hero.name, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_list -> {
                binding.rvHeroes.layoutManager = LinearLayoutManager(this)
            }

            R.id.action_grid -> {
                binding.rvHeroes.layoutManager = GridLayoutManager(this, 2)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}