package com.example.submission2githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FollowersAdapter(private val listFollowers: ArrayList<User>) : RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUsername: TextView = view.findViewById(R.id.txt_username)
        val imgAvatar: ImageView = view.findViewById(R.id.img_avatar)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.user_item, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvUsername.text = listFollowers[position].username
        Glide.with(viewHolder.itemView.context)
            .load(listFollowers[position].avatar)
            .circleCrop()
            .into(viewHolder.imgAvatar)
    }

    override fun getItemCount() = listFollowers.size
}