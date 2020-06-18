package com.example.androidassignment.views

interface LoginViewPresenter {

    interface LoginView {
        fun initViews()
        fun onError(errorCode: Int)
        fun isInternetConnected(): Boolean
        fun noInternet()
        fun loading()
        fun onSuccessfulLogin()
        fun username(): String
        fun password(): String
    }

    interface LoginPresenter {
        fun attachView(view: LoginView)
        fun isValidInput(): Boolean
        fun fetchData()
        fun onDestroy()
    }
}