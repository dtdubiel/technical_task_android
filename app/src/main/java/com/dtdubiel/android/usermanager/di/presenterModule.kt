package com.dtdubiel.android.usermanager.di

import com.dtdubiel.android.usermanager.feature.add_user.AddUserPresenter
import com.dtdubiel.android.usermanager.feature.add_user.AddUserView
import com.dtdubiel.android.usermanager.feature.add_user.IAddUserPresenter
import com.dtdubiel.android.usermanager.feature.users_list.IUsersPresenter
import com.dtdubiel.android.usermanager.feature.users_list.UsersPresenter
import com.dtdubiel.android.usermanager.feature.users_list.UsersView
import org.koin.dsl.module

val presenterModule = module {

    factory<IUsersPresenter> { (view: UsersView) ->
        UsersPresenter(view = view, remoteDataSource = get())
    }

    factory<IAddUserPresenter> { (view: AddUserView) ->
        AddUserPresenter(view = view)
    }

}
