package com.example.githubapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.adapter.UserListAdapter
import com.example.githubapp.databinding.FragmentFollowBinding
import com.example.githubapp.viewmodel.FollowViewModel

class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UserListAdapter
    private val viewModel: FollowViewModel by viewModels()

    private var username: String = ""
    private var isFollowers: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            username = it.getString(ARG_USERNAME) ?: ""
            isFollowers = it.getBoolean(ARG_IS_FOLLOWERS, true)
        }

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = UserListAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.userList.observe(viewLifecycleOwner) { userList ->
            adapter.submitList(userList)
        }

        viewModel.isEmpty.observe(viewLifecycleOwner) { isEmpty ->
            binding.emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
            binding.emptyView.text = "User tidak memiliki ${if (isFollowers) "followers" else "following"}"
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        if (viewModel.userList.value.isNullOrEmpty()) {
            viewModel.fetchFollowData(username, isFollowers)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_USERNAME = "username"
        const val ARG_IS_FOLLOWERS = "is_followers"

        fun newInstance(username: String, isFollowers: Boolean): FollowFragment {
            val fragment = FollowFragment()
            val args = Bundle()
            args.putString(ARG_USERNAME, username)
            args.putBoolean(ARG_IS_FOLLOWERS, isFollowers)
            fragment.arguments = args
            return fragment
        }
    }
}




