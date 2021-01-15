package com.kho.pagedv3.core.model

data class UserResponse(
    val code: Int,
    val data: List<DataUser>,
    val meta: Meta
)

data class DataUser(
    val created_at: String,
    val email: String,
    val gender: String,
    val id: Int,
    val name: String,
    val status: String,
    val updated_at: String
)

data class Meta(
    val pagination: Pagination
)

data class Pagination(
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)