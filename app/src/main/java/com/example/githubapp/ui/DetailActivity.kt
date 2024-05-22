package com.example.githubapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubapp.db.UserFavorite
import com.example.githubapp.R
import com.example.githubapp.viewmodel.ViewModelFactory
import com.example.githubapp.adapter.SectionsPagerAdapter
import com.example.githubapp.api.response.DetailUserResponse
import com.example.githubapp.databinding.ActivityDetailBinding
import com.example.githubapp.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private var detailUser = DetailUserResponse()
    private var buttonState: Boolean = false
    private var userFavorite: UserFavorite? = null
    private var username: String = ""
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        detailViewModel = obtainViewModel(this@DetailActivity)

        username = intent.getStringExtra("username") ?: ""
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Followers"
                1 -> "Following"
                else -> ""
            }
        }.attach()

        if (viewModel.userDetails.value == null) {
            viewModel.fetchUserDetails(username)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.userDetails.observe(this) { userDetails ->
            userDetails?.let { displayUserDetails(it) }
        }

        viewModel.error.observe(this) { error ->
            showToast(error)
        }

        detailViewModel.userDetails.observe(this) { detailList ->
            detailUser = detailList
            displayUserDetails(detailUser)
            userFavorite = UserFavorite(detailUser.id!!, detailUser.login)
            detailViewModel.getAllFavorites().observe(this) { favoriteList ->
                if (favoriteList != null) {
                    for (data in favoriteList) {
                        if (detailUser.id == data.id) {
                            buttonState = true
                            binding.floatinglove.setImageResource(R.drawable.ic_favorited)
                        }
                    }
                }
            }

            binding.floatinglove.setOnClickListener {
                if (!buttonState) {
                    buttonState = true
                    binding.floatinglove.setImageResource(R.drawable.ic_favorited)
                    insertToDB(detailUser)
                } else {
                    buttonState = false
                    binding.floatinglove.setImageResource(R.drawable.ic_unfavorite)
                    detailViewModel.delete(detailUser.id!!)
                    showToast("User favorit anda sudah dihapus.")
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun insertToDB(detailList: DetailUserResponse) {
        userFavorite.let { userFavorite ->
            userFavorite?.id = detailList.id!!
            userFavorite?.login = detailList.login
            userFavorite?.avatarUrl = detailList.avatarUrl
            detailViewModel.insert(userFavorite as UserFavorite)
            showToast("User sudah menjadi favorit")
        }
    }

    private fun displayUserDetails(user: DetailUserResponse) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(user.avatarUrl)
                .into(imageViewProfile)

            textViewUsername.text = user.login
            textViewName.text = user.name
            textViewFollowersCount.text = user.followers.toString()
            textViewFollowingCount.text = user.following.toString()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_share, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.bagikan -> {
                shareUserDetails()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareUserDetails() {
        val user = viewModel.userDetails.value ?: return
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out ${user.login}'s profile on GitHub: ${user.htmlUrl}")
        startActivity(Intent.createChooser(shareIntent, "Share ${user.login}'s profile"))
    }
}



