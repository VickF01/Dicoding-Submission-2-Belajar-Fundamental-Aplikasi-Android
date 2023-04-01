package com.example.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.databinding.FragmentFollowersBinding
import com.example.githubuser.ui.adapter.UserAdapter
import com.example.githubuser.viewmodel.DetailViewModel

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        binding?.rvFollowers?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding?.rvFollowers?.addItemDecoration(itemDecoration)
        val detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        detailViewModel.detailFollowers.observe(viewLifecycleOwner) {detailFollowers ->
            setUserFollowers(detailFollowers)
        }
    }

    private fun setUserFollowers(userFollowers: List<ItemsItem>) {
        val list = ArrayList<ItemsItem>()
        for (user in userFollowers) {
            list.addAll(listOf(user))
        }
        binding?.rvFollowers?.adapter = UserAdapter(list)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}