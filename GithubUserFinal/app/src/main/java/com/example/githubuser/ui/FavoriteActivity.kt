package com.example.githubuser.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.local.entity.UserEntity
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.ui.adapter.UserAdapter
import com.example.githubuser.viewmodel.FavoriteViewModel
import com.example.githubuser.viewmodel.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel> { FavoriteViewModelFactory.getInstance(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        binding.rvFavorite.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        favoriteViewModel.getAllFavorite().observe(this) { users: List<UserEntity> ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.login, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            binding.rvFavorite.adapter = UserAdapter(items)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}