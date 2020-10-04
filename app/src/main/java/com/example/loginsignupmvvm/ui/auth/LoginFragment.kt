package com.example.loginsignupmvvm.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.loginsignupmvvm.data.network.AuthApi
import com.example.loginsignupmvvm.data.network.Resource
import com.example.loginsignupmvvm.data.repo.AuthRepository
import com.example.loginsignupmvvm.databinding.FragmentLoginBinding
import com.example.loginsignupmvvm.ui.base.BaseFragment
import com.example.loginsignupmvvm.ui.enable
import com.example.loginsignupmvvm.ui.handleApiError
import com.example.loginsignupmvvm.ui.home.HomeActivity
import com.example.loginsignupmvvm.ui.startNewActivity
import com.example.loginsignupmvvm.ui.viewmodel.AuthViewModel
import com.example.loginsignupmvvm.ui.visible
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressBar.visible(false)
        binding.btnLogin.enable(false)
        viewModel.loginResponse.observe(viewLifecycleOwner, {
            binding.progressBar.visible(false)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value.user.access_token)
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }

                }
                is Resource.Failure -> handleApiError(it)
            }
        })

        binding.etPassword.addTextChangedListener {
            val password = binding.etPassword.text.toString().trim()
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            // add input validations
            binding.progressBar.visible(true)
            viewModel.login(email, password)
        }

    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)

}