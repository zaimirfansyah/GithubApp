package com.example.githubapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.api.response.ItemsItem
import com.example.githubapp.api.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _userList = MutableLiveData<List<ItemsItem>>()
    val userList: LiveData<List<ItemsItem>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    fun fetchFollowData(username: String, isFollowers: Boolean) {
        _isLoading.value = true
        val call = if (isFollowers) {
            ApiConfig.getApiService().getFollowers(username)
        } else {
            ApiConfig.getApiService().getFollowing(username)
        }
        call.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userList = response.body()
                    _userList.value = userList ?: emptyList()
                    checkListAndSetEmptyMessage(userList ?: emptyList())
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    private fun checkListAndSetEmptyMessage(userList: List<ItemsItem> ) {
        _isEmpty.value = userList.isEmpty()
    }
}

