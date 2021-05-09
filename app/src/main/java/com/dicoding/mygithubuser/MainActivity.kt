package com.dicoding.mygithubuser

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuser.adapter.UserAdapter
import com.dicoding.mygithubuser.databinding.ActivityMainBinding
import com.dicoding.mygithubuser.model.UserItems
import com.dicoding.mygithubuser.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var adapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    val list = ArrayList<UserItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLabel(true)

        supportActionBar?.hide()

        showMainViewModel()
        showRecyclerList()

        binding.imageFavorite.setOnClickListener {
            val mIntent = Intent(this, FavoriteActivity::class.java)
            startActivity(mIntent)
        }
    }

    private fun showMainViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        searchView = binding.searchView

        mainViewModel.getListUser().observe(this, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
                showLabel(false)
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query != "") {
                    list.clear()
                    showLoading(true)
                    showLabel(false)
                    mainViewModel.setListUser(query)
                } else {
                    showSnackBarMessage("Masukkan username")
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.menu_favorite -> {
//                val mIntent = Intent(this, FavoriteActivity::class.java)
//                startActivity(mIntent)
//            }
//            R.id.menu_settings -> {
//                val mIntent = Intent(this, ReminderActivity::class.java)
//                startActivity(mIntent)
//            }
//            android.R.id.home -> {
//                try {
//                    val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com"))
//                    startActivity(myIntent)
//                } catch (e: ActivityNotFoundException) {
//                    Toast.makeText(
//                        this, "No application can handle this request."
//                                + " Please install a webbrowser", Toast.LENGTH_LONG
//                    ).show()
//                    e.printStackTrace()
//                }
//            }
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

    private fun showRecyclerList() {
        showLoading(false)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.adapter = adapter
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if(state) View.VISIBLE else View.GONE
    }

    private fun showLabel(state: Boolean) {
        binding.tvFirstLoad.visibility = if(state) View.VISIBLE else View.GONE
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.rvUsers, message, Snackbar.LENGTH_SHORT).show()
    }

}