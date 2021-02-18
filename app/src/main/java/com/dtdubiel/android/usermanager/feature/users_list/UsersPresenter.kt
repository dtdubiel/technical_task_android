package com.dtdubiel.android.usermanager.feature.users_list

import com.dtdubiel.android.networking.RemoteDataSource
import com.dtdubiel.android.usermanager.base.BasePresenter

interface IUsersPresenter {
    fun onStart()
    fun onDestroy()
    fun removeUser(id: Int)
    fun addUser(username: String, email: String)
}

class UsersPresenter(private val view: UsersView, private val remoteDataSource: RemoteDataSource) :
    BasePresenter(), IUsersPresenter {

    override fun onStart() {
        refreshUsers()
    }

    override fun removeUser(id: Int) {
        view.lockAndLoad(
            call = remoteDataSource.removeUser(id),
            onSuccess = {
                refreshUsers()
            },
            onError = { error ->
                error.localizedMessage?.let {
                    view.showError(it)
                }
            }
        )
    }

    override fun addUser(username: String, email: String) {
        view.lockAndLoad(
            call = remoteDataSource.addUser(username, email),
            onSuccess = {
                refreshUsers()
            },
            onError = { error ->
                error.localizedMessage?.let {
                    view.showError(it)
                }
            }
        )
    }

    private fun refreshUsers() {
        view.lockAndLoad(
            call = remoteDataSource.getUsers(),
            onSuccess = {
                view.populateUserList(it)
            },
            onError = { error ->
                error.localizedMessage?.let {
                    view.showError(it)
                }
            })
    }
}