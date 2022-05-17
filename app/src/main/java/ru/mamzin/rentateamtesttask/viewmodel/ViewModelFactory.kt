package ru.mamzin.rentateamtesttask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mamzin.rentateamtesttask.repository.NetRepository

class ViewModelFactory constructor(private val repository: NetRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NetViewModel::class.java)) {
            NetViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}