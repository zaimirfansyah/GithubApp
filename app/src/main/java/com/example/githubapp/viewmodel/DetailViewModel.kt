package com.example.githubapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.repo.Repository
import com.example.githubapp.db.UserFavorite
import com.example.githubapp.api.retrofit.ApiConfig
import com.example.githubapp.api.response.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val _userDetails = MutableLiveData<DetailUserResponse>()
    val userDetails: LiveData<DetailUserResponse>
        get() = _userDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val mRepository: Repository =
        Repository(application)

    fun insert(user: UserFavorite) {
        mRepository.insert(user)
    }

    fun delete(id: Int) {
        mRepository.delete(id)
    }

    fun getAllFavorites(): LiveData<List<UserFavorite>> = mRepository.getAllFavorites()

    fun fetchUserDetails(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val user = response.body()
                        user?.let {
                            _userDetails.value = it
                        }
                    } else {
                        _error.value = "Error: ${response.code()} - ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = "Error: ${t.message}"
                }
            })
    }
}
