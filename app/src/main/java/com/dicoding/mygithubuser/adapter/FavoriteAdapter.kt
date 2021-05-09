package com.dicoding.mygithubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.mygithubuser.DetailUserActivity
import com.dicoding.mygithubuser.R
import com.dicoding.mygithubuser.model.UserItems
import com.dicoding.mygithubuser.databinding.ItemUserFavoriteBinding

class FavoriteAdapter(private val favList: List<UserItems>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_favorite, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favList[position])
        val user = favList[position]

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intentDetailActivity = Intent(context, DetailUserActivity::class.java)
            intentDetailActivity.putExtra(DetailUserActivity.EXTRA_USER, user)
            context.startActivity(intentDetailActivity)
        }
    }

    override fun getItemCount(): Int = favList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserFavoriteBinding.bind(itemView)
        fun bind(userFav: UserItems) {
            Glide.with(itemView.context)
                .load(userFav.avatar)
                .apply(RequestOptions().override(55, 55))
                .into(binding.imgAvatarFav)
            binding.tvItemNameFav.text = userFav.username

        }
    }
}