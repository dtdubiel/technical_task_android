package com.dtdubiel.android.usermanager.feature.users_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dtdubiel.android.networking.model.User
import com.dtdubiel.android.usermanager.R
import com.dtdubiel.android.usermanager.adapters.UsersListAdapter
import com.dtdubiel.android.usermanager.base.BaseController
import com.dtdubiel.android.usermanager.base.IBaseView
import com.dtdubiel.android.usermanager.feature.add_user.AddUserDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

interface UsersView: IBaseView {
    fun populateUserList(result: List<User>)
    fun onUserRemovedSuccess()
}

class UsersScreen : BaseController(), UsersView {

    private val presenter by inject<IUsersPresenter> { parametersOf(this) }

    private val userListAdapter = UsersListAdapter {
        presenter.removeUser(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View = inflater.inflate(
        R.layout.users_controller, container, false
    ).apply {
        findViewById<RecyclerView>(R.id.usersRecyclerView).apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.VERTICAL
            }
            itemAnimator = DefaultItemAnimator()
            adapter = userListAdapter
            addItemDecoration(
                DividerItemDecoration(context, RecyclerView.VERTICAL)
            )
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            navigation.show(AddUserDialog.create { username, email ->
                presenter.addUser(username, email)
            })
        }
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        presenter.onStart()
    }

    override fun onDestroyView(view: View) {
        presenter.onDestroy()
        super.onDestroyView(view)
    }

    override fun populateUserList(result: List<User>) {
        userListAdapter.submitList(result)
    }

    override fun onUserRemovedSuccess() {
        view?.let {
            Snackbar.make(it, it.context.getString(R.string.user_removed_success), Snackbar.LENGTH_LONG).show()
        }
    }

}
