package com.dtdubiel.android.networking

import com.dtdubiel.android.networking.model.UserRequest

class RemoteDataSource(private val goRestApi: GoRestApi) {

    fun getUsers() = goRestApi.getUsers(null).flatMap { response ->
        goRestApi.getUsers(response.meta.pagination.pages)
    }
        .map { it.data }

    fun removeUser(id: Int) = goRestApi.removeUser(id)

    fun addUser(username: String, email: String) =
        goRestApi.addUser(UserRequest(name = username, email = email))

}
