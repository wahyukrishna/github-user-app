package com.dicoding.mygithubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuser.adapter.FavoriteAdapter
import com.dicoding.mygithubuser.databinding.ActivityFavoriteBinding
import com.dicoding.mygithubuser.model.UserItems
import com.dicoding.mygithubuser.viewmodel.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteBinding
    private val listFav = mutableListOf<UserItems>()
    private val adapter = FavoriteAdapter(listFav)
    private lateinit var viewModel : FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        showFavoriteViewModel()
        binding.rvFavoriteUser.apply {
            setHasFixedSize(true)
            adapter = this@FavoriteActivity.adapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }

        binding.btHome.setOnClickListener {
            finish()
        }

    }

    private fun showFavoriteViewModel() {
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.getFav()
        viewModel.favList.observe(this, { userFav ->
            if(userFav.isNotEmpty()){
                listFav.clear()
                listFav.addAll(userFav)
                adapter.notifyDataSetChanged()
            }else{
                showSnackBarMessage("No Data")
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_settings -> {
                val mIntent = Intent(this, ReminderActivity::class.java)
                startActivity(mIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.rvFavoriteUser, message, Snackbar.LENGTH_SHORT).show()
    }

}