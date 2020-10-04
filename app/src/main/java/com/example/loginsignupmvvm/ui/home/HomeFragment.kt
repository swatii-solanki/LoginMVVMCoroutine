package com.example.loginsignupmvvm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginsignupmvvm.data.network.Resource
import com.example.loginsignupmvvm.data.network.UserApi
import com.example.loginsignupmvvm.data.repo.UserRepository
import com.example.loginsignupmvvm.data.responses.User
import com.example.loginsignupmvvm.databinding.FragmentHomeBinding
import com.example.loginsignupmvvm.ui.base.BaseFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()
        viewModel.user.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    updateUI(it.value.user)
                }

                is Resource.Loading -> {
                    //binding.progressBar.isLoading = true
                }
            }
        })
    }

    private fun updateUI(user: User) {

    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDataSource.buildApi(UserApi::class.java)
        return UserRepository(api)
    }


}