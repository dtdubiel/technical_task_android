package com.dtdubiel.android.networking.model

data class UsersResponse(val code: Int, val meta: Meta, val data: List<User>)

data class Meta(val pagination: Pagination)
data class Pagination(val total: Long, val pages: Long, val page: Long, val limit: Long)
data class User(val id: Long, val name: String, val email: String, val created_at: String)


data class UserRequest(val email: String, val name: String, val gender: String = "Female", val status: String = "Active")