package ru.mamzin.rentateamtesttask.repository

import ru.mamzin.rentateamtesttask.net.RetrofitService

class NetRepository(private val retrofitService: RetrofitService) {

    fun getAllStrings() = retrofitService.getStrings()

}