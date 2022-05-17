package ru.mamzin.rentateamtesttask.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.mamzin.rentateamtesttask.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getUsers(): LiveData<MutableList<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: List<User>)
}