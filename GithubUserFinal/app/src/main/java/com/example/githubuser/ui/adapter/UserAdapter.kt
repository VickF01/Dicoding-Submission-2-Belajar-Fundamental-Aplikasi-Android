package com.example.githubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.ui.UserDetailActivity

class UserAdapter(private val listUser: ArrayList<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    class ViewHolder (var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvUser.text = listUser[position].login

        Glide.with(holder.itemView.context)
            .load(listUser[position].avatarUrl)
            .circleCrop()
            .format(DecodeFormat.PREFER_RGB_565)
            .into(holder.binding.ivProfile)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.EXTRA_LOGIN, listUser[holder.adapterPosition])
            holder.itemView.context.startActivity(intent)
        }
    }
}