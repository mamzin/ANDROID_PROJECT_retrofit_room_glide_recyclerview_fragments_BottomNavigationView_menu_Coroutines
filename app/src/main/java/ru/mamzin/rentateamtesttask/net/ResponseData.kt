package ru.mamzin.rentateamtesttask.net

data class ResponseData<T>(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: T
)