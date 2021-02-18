package com.dtdubiel.android.usermanager.feature.add_user

import com.dtdubiel.android.usermanager.base.BasePresenter
import com.dtdubiel.android.usermanager.utils.isEmailValid

interface IAddUserPresenter {
    fun consumeInputs(usernameInput: String, emailInput: String)
}

class AddUserPresenter(private val view: AddUserView): BasePresenter(), IAddUserPresenter {

    override fun consumeInputs(usernameInput: String, emailInput: String) {
        view.enableAddButton(usernameInput.isNotBlank() && isEmailValid(emailInput))
    }

}

