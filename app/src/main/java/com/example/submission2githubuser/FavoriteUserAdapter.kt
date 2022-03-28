package com.example.submission2githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FavoriteUserAdapter(private val listFavorite: ArrayList<User>) : RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUsername: TextView = view.findViewById(R.id.txt_username)
        val imgAvatar: ImageView = view.findViewById(R.id.img_avatar)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.user_item, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvUsername.text = listFavorite[position].username
        Glide.with(viewHolder.itemView.context)
            .load(listFavorite[position].avatar)
            .circleCrop()
            .into(viewHolder.imgAvatar)

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(viewHolder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.USERNAME, listFavorite[position].username)
            viewHolder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }
}