package com.example.loginsignupmvvm.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loginsignupmvvm.data.repo.AuthRepository
import com.example.loginsignupmvvm.data.repo.BaseRepository
import com.example.loginsignupmvvm.data.repo.UserRepository
import com.example.loginsignupmvvm.ui.home.HomeViewModel
import com.example.loginsignupmvvm.ui.viewmodel.AuthViewModel

class ViewModelFactory(private val repository: BaseRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as UserRepository) as T
            else -> throw IllegalAccessException("View Model class not found")
        }
    }
}