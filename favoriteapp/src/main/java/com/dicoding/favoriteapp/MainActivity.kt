package com.dicoding.favoriteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.favoriteapp.adapter.FavoriteAdapter
import com.dicoding.favoriteapp.databinding.ActivityMainBinding
import com.dicoding.favoriteapp.viewmodel.MainViewModel
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(true)

        showRecyclerList()
        showMainViewModel()

    }

    override fun onResume() {
        super.onResume()
        showMainViewModel()
    }



    private fun showMainViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.setListUser(this)

        viewModel.getListUser().observe(this, { users ->
            if(users != null){
                adapter.setData(users)
            }
        })

    }

    private fun showRecyclerList() {
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()
        binding.rvFavorite.adapter = adapter
        binding.rvFavorite.setHasFixedSize(true)

    }


}