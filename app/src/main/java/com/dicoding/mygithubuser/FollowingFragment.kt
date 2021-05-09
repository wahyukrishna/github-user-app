package com.dicoding.mygithubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuser.adapter.UserAdapter
import com.dicoding.mygithubuser.databinding.FragmentFollowingBinding
import com.dicoding.mygithubuser.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: UserAdapter
    private lateinit var followingViewModel: FollowingViewModel

    companion object {
        var EXTRA_USER_NAME = "username"

        fun newInstance(username: String?): FollowingFragment {
            val mfragment = FollowingFragment()
            val mbundle = Bundle()
            mbundle.putString(EXTRA_USER_NAME, username)
            mfragment.arguments = mbundle
            return mfragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)

        showLoading(true)
        showRecyclerView()
        showFollowingViewModel()


        return binding.root
    }

    private fun showFollowingViewModel() {
        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        followingViewModel.getListFollowing().observe(viewLifecycleOwner, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })
    }

    private fun showRecyclerView() {
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvFragmentFollowing.setHasFixedSize(true)
        binding.rvFragmentFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFragmentFollowing.adapter = adapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(EXTRA_USER_NAME)
        followingViewModel.setListFollowing(username)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBarFollowing.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowing.visibility = View.GONE
        }
    }
}