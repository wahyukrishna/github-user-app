package com.dicoding.mygithubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.dicoding.mygithubuser.databinding.ActivityDetailUserBinding
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.mygithubuser.adapter.SectionPagerAdapter
import com.dicoding.mygithubuser.model.UserItems
import com.dicoding.mygithubuser.viewmodel.DetailUserViewModel
import com.dicoding.mygithubuser.viewmodel.FavoriteViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.android.synthetic.main.item_row_user.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var viewModel: FavoriteViewModel

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private var TAB_TITLES = intArrayOf(
            R.string.tab_text_follower,
            R.string.tab_text_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<UserItems>(EXTRA_USER) as UserItems
        val id = user.id
        val username = user.username
        val avatar = user.avatar

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = username

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = username
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        showDetailUserViewModel(username)

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        var statusFavorite = false
        CoroutineScope(Dispatchers.IO).launch {
            val getId = id.let { viewModel.checkFavorite(it) }
            withContext(Dispatchers.Main){
                statusFavorite = getId != null && getId>0
                setStatusFavorite(statusFavorite)
            }
        }

        binding.fabFav.setOnClickListener {
            statusFavorite = !statusFavorite
                if(statusFavorite){
                    try{
                        CoroutineScope(Dispatchers.IO).launch {
                            val users = detailUserViewModel.userDetail.value?.let { it1 ->
                                UserItems(id,
                                    username.toString(),
                                    avatar.toString(),
                                    it1.name,
                                    it1.location,
                                    it1.company,
                                    it1.repo,
                                )
                            }
                            if (users != null) {
                                viewModel.insertFav(users)
                            }
                        }
                        Toast.makeText(this, R.string.added, Toast.LENGTH_SHORT).show()

                    }catch (e: Exception){
                        e.printStackTrace()
                        Toast.makeText(this, R.string.error_insert, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.deleteFav(id)
                    }
                    Toast.makeText(this, R.string.deleted, Toast.LENGTH_SHORT).show()
                }
                setStatusFavorite(statusFavorite)

        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if(statusFavorite){
            binding.fabFav.setImageResource(R.drawable.ic_star_white_48)
        }else{
            binding.fabFav.setImageResource(R.drawable.ic_star_outline_48)
        }

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
            R.id.menu_favorite -> {
                val mIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(mIntent)
            }
            R.id.menu_settings -> {
                val mIntent = Intent(this, ReminderActivity::class.java)
                startActivity(mIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDetailUserViewModel(user: String?) {
        showLoading(true)
        detailUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)
        detailUserViewModel.setUserDetail(user)

        detailUserViewModel.getUserDetail().observe(this, ::observeUserDetail)

    }

    private fun observeUserDetail(userItems: UserItems) {
            binding.apply {
                Glide.with(this@DetailUserActivity)
                    .load(userItems.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(detailAvatar)
                detailName.text = userItems.name
                detailLocation.text = userItems.location
                detailCompany.text = userItems.company
                detailRepo.text = userItems.repo.toString()
                showLoading(false)
            }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarDetail.visibility = if(state) View.VISIBLE else View.GONE
    }

}