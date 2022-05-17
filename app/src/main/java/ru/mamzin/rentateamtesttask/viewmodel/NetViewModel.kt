package ru.mamzin.rentateamtesttask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mamzin.rentateamtesttask.model.User
import ru.mamzin.rentateamtesttask.net.ResponseData
import ru.mamzin.rentateamtesttask.repository.NetRepository

class NetViewModel constructor(private val repository: NetRepository) : ViewModel() {

    val dataList = MutableLiveData<List<User>?>()
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    fun getAllUsers() {
        val response = repository.getAllStrings()
        loading.postValue(true)
        response.enqueue(object : Callback<ResponseData<List<User>>> {
            override fun onResponse(
                call: Call<ResponseData<List<User>>>,
                response: Response<ResponseData<List<User>>>
            ) {
                dataList.postValue(response.body()?.data)
                loading.value = false
            }

            override fun onFailure(call: Call<ResponseData<List<User>>>, t: Throwable) {
                errorMessage.postValue(t.message)
                loading.value = false
            }
        })
    }
}