package ru.mamzin.rentateamtesttask.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mamzin.rentateamtesttask.database.UserDatabase
import ru.mamzin.rentateamtesttask.model.User
import ru.mamzin.rentateamtesttask.repository.DBRepository


class DBViewModel(application: Application) : AndroidViewModel(application) {

    val allUsers: LiveData<MutableList<User>>
    private val repository: DBRepository

    init {
        val dao = UserDatabase.getDatabase(application).userDao()
        repository = DBRepository(dao)
        allUsers = repository.allUsers
    }

    fun addUser(userlist: List<User>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(userlist)
    }

}