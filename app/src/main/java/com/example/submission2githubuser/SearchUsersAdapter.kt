package com.example.submission2githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SearchUsersAdapter(private val listUsers: ArrayList<User>) : RecyclerView.Adapter<SearchUsersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_user_name)
        val imgAvatar: ImageView = view.findViewById(R.id.img_user_photo)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvName.text = listUsers[position].username
        Glide.with(viewHolder.itemView.context)
            .load(listUsers[position].avatar)
            .circleCrop()
            .into(viewHolder.imgAvatar)

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(viewHolder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.USERNAME, listUsers[position].username)
            viewHolder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listUsers.size


}