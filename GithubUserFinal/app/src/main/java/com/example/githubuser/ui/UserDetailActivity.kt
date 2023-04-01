package com.example.githubuser.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.local.entity.UserEntity
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.databinding.ActivityUserDetailBinding
import com.example.githubuser.ui.adapter.SectionsPagerAdapter
import com.example.githubuser.viewmodel.DetailViewModel
import com.example.githubuser.viewmodel.FavoriteViewModel
import com.example.githubuser.viewmodel.FavoriteViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private val favoriteViewModel by viewModels<FavoriteViewModel> { FavoriteViewModelFactory.getInstance(application) }
    private var isFavorite = false


    companion object {
        const val EXTRA_LOGIN = "extra_login"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userData = intent.getParcelableExtra("extra_login") as ItemsItem?
        userData?.let {
            detailViewModel.getUserData(it.login)

            detailViewModel.isLoading.observe(this) {
                showLoading(it)
            }

            detailViewModel.username.observe(this) { username ->
                binding.tvUsernameDetail.text = username
            }

            detailViewModel.name.observe(this) { name ->
                binding.tvNameDetail.text = name
            }

            detailViewModel.picture.observe(this) { picture ->
                Glide.with(this@UserDetailActivity)
                    .load(picture).circleCrop().into(binding.ivProfileDetail)
            }

            detailViewModel.following.observe(this) { following ->
                binding.tvFollowing.text = resources.getString(R.string.following_num, following)
            }

            detailViewModel.followers.observe(this) { followers ->
                binding.tvFollowers.text = resources.getString(R.string.followers_num, followers)
            }

            supportActionBar?.title = it.login
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            detailViewModel.getUserFollowers(it.login)
            detailViewModel.getUserFollowing(it.login)

            favoriteViewModel.getFavoriteUser(userData.login).observe(this) { favUser ->
                isFavorite = favUser.isNotEmpty()

                if (favUser.isEmpty()) {
                    binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(binding.fabFavorite.context, R.drawable.baseline_favorite_border_24))
                } else {
                    binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(binding.fabFavorite.context, R.drawable.baseline_favorite_24))
                }
            }

            binding.fabFavorite.setOnClickListener {
                val userEntity = UserEntity(userData.login, userData.avatarUrl)
                if (isFavorite) {
                    favoriteViewModel.delete(userEntity)
                    Toast.makeText(this@UserDetailActivity, "${userEntity.login} deleted from your favorite", Toast.LENGTH_LONG).show()
                } else {
                    favoriteViewModel.insert(userEntity)
                    Toast.makeText(this@UserDetailActivity, "${userEntity.login} added to your favorite", Toast.LENGTH_LONG).show()
                }
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}