package com.example.githubapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.githubapp.api.response.GithubResponse
import com.example.githubapp.api.response.ItemsItem
import com.example.githubapp.api.retrofit.ApiConfig
import com.example.githubapp.tema.TemaManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: TemaManager) : ViewModel() {

    private val _userList = MutableLiveData<List<ItemsItem>>()
    val userList: LiveData<List<ItemsItem>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    init {
        fetchGithubUsers("example")
    }

    fun fetchGithubUsers(query: String) {
        _isLoading.value = true
        val call = if (query.isNotBlank()) {
            ApiConfig.getApiService().searchGithubUsers(query)
        } else {
            ApiConfig.getApiService().getGithubUsers("example")
        }
        call.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userList = response.body()?.items ?: emptyList()
                    _userList.value = userList
                    _isEmptyList.value = userList.isEmpty()
                }
                else {
                    _error.value = "Error: ${response.code()} - ${response.message()}"
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Error: ${t.message}"
            }
        })
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}

