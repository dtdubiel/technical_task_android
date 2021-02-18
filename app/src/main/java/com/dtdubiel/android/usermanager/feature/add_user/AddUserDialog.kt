package com.dtdubiel.android.usermanager.feature.add_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.dtdubiel.android.usermanager.R
import com.dtdubiel.android.usermanager.base.BaseController
import com.dtdubiel.android.usermanager.navigation.Dialog
import com.dtdubiel.android.usermanager.utils.hideKeyboard
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

interface AddUserView {
    fun enableAddButton(enable: Boolean)
}

class AddUserDialog : BaseController(), Dialog, AddUserView {

    private lateinit var onAddUserAction: (String, String) -> Unit

    private val presenter by inject<IAddUserPresenter> { parametersOf(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View = inflater.inflate(R.layout.add_user_dialog, container, false).apply {
        val usernameET = findViewById<TextView>(R.id.usernameInput)
        val emailET = findViewById<TextView>(R.id.emailInput)
        findViewById<Button>(R.id.addUserBtn).setOnClickListener {
            onAddUserAction(
                usernameET.text.toString(),
                emailET.text.toString()
            )
            hideKeyboard()
            navigation.onBack()
        }
        usernameET.doAfterTextChanged {
            presenter.consumeInputs(usernameET.text.toString(), emailET.text.toString() )
        }
        emailET.doAfterTextChanged {
            presenter.consumeInputs(usernameET.text.toString(), emailET.text.toString() )
        }
    }

    companion object {
        fun create(onAddUserAction: (String, String) -> Unit) = AddUserDialog().apply {
            this.onAddUserAction = onAddUserAction
        }
    }

    override fun enableAddButton(enable: Boolean) {
        view?.run {
            findViewById<Button>(R.id.addUserBtn).isEnabled = enable
        }
    }

}
