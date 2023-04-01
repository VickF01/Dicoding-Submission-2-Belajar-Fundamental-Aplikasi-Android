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
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.ui.adapter.UserAdapter
import com.example.githubuser.viewmodel.DetailViewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        binding?.rvFollowing?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding?.rvFollowing?.addItemDecoration(itemDecoration)
        val detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        detailViewModel.detailFollowing.observe(viewLifecycleOwner) {detailFollowing ->
            setUserFollowing(detailFollowing)
        }
    }

    private fun setUserFollowing(userFollowing: List<ItemsItem>) {
        val list = ArrayList<ItemsItem>()
        for (user in userFollowing) {
            list.addAll(listOf(user))
        }
        binding?.rvFollowing?.adapter = UserAdapter(list)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}