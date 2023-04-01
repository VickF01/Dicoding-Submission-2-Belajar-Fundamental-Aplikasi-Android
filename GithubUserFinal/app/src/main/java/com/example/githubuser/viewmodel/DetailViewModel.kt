package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.data.remote.response.UserDetailResponse
import com.example.githubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class DetailViewModel : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: MutableLiveData<String> = _username

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _picture = MutableLiveData<String>()
    val picture: LiveData<String> = _picture

    private val _followers = MutableLiveData<String>()
    val followers: LiveData<String> = _followers

    private val _following = MutableLiveData<String>()
    val following: LiveData<String> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailFollowers = MutableLiveData<List<ItemsItem>>()
    val detailFollowers: LiveData<List<ItemsItem>> = _detailFollowers

    private val _detailFollowing = MutableLiveData<List<ItemsItem>>()
    val detailFollowing: LiveData<List<ItemsItem>> = _detailFollowing

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getUserData(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback, retrofit2.Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _username.value = response.body()?.login
                        _name.value = response.body()?.name
                        _picture.value = response.body()?.avatarUrl
                        _followers.value = response.body()?.followers.toString()
                        _following.value = response.body()?.following.toString()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getUserFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback, retrofit2.Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailFollowers.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUserFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback, retrofit2.Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailFollowing.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}