package ru.mamzin.rentateamtesttask.repository

import androidx.lifecycle.LiveData
import ru.mamzin.rentateamtesttask.database.UserDao
import ru.mamzin.rentateamtesttask.model.User

class DBRepository(private val userDao: UserDao) {

    val allUsers: LiveData<MutableList<User>> = userDao.getUsers()

    suspend fun insert(userlist: List<User>) {
        userDao.addUser(userlist)
    }

}