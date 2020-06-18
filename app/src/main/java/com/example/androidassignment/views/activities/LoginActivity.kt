package com.example.androidassignment.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.androidassignment.R
import com.example.androidassignment.network.NetworkConnection
import com.example.androidassignment.preferences.ModelPreferencesManager
import com.example.androidassignment.presenter.LoginPresenter
import com.example.androidassignment.views.LoginViewPresenter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class LoginActivity : AppCompatActivity(), LoginViewPresenter.LoginView {

    lateinit var loginViewPresenter: LoginViewPresenter.LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUpToolbar()
        ModelPreferencesManager.preferences.getBoolean(IS_LOGGED_IN, false)
        loginViewPresenter = LoginPresenter()
        loginViewPresenter.attachView(this)
    }

    override fun initViews() {
        login.setOnClickListener {
            if (loginViewPresenter.isValidInput()) {
                loginViewPresenter.fetchData()
            } else {
                username.error = getString(R.string.empty_text_error, "Username")
                password.error = getString(R.string.empty_text_error, "Password")
            }
        }
    }

    override fun onError(errorCode: Int) {
        login.isEnabled = true
        login.visibility = View.VISIBLE
        loading_lottie.visibility = View.GONE
        if (errorCode == INVALID_CREDENTIAL) {
            invalid_credential.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, getString(R.string.server_error), Toast.LENGTH_LONG).show()
        }
    }

    override fun isInternetConnected(): Boolean = NetworkConnection.isNetworkConnected(this)

    override fun noInternet() {
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
    }

    override fun loading() {
        login.isEnabled = false
        loading_lottie.visibility = View.VISIBLE
        login.visibility = View.GONE
        invalid_credential.visibility = View.GONE
    }

    override fun onSuccessfulLogin() {
        val messageIntent = Intent(this, MessagesActivity::class.java)
        startActivity(messageIntent)
        clearInputField()
        finish()
    }

    override fun username(): String = input_username.text.toString()

    override fun password(): String = input_password.text.toString()

    private fun clearInputField() {
        input_username.setText("")
        input_password.setText("")
    }

    private fun setUpToolbar() {
        val toolbar = tool_bar.findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        tool_bar_title.text = getString(R.string.login_lable)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onDestroy() {
        loginViewPresenter.onDestroy()
        super.onDestroy()
    }

    companion object {
        const val IS_LOGGED_IN = "IS_LOGIN"
        const val AUTH_TOKEN = "AUTH_TOKEN"
        const val THREAD_DETAILS = "THREAD_DETAILS"
        const val INVALID_CREDENTIAL = 401
    }
}
