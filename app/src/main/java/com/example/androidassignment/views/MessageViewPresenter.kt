package com.example.androidassignment.views

import com.example.androidassignment.model.AuthToken
import com.example.androidassignment.model.Message

interface MessageViewPresenter {

    interface MessageView {
        fun initViews(authToken: AuthToken?)
        fun updateData(posts: List<Message>)
        fun onError()
        fun isInternetConnected(): Boolean
        fun noInternet()
        fun loading()
        fun launchLogin()
    }

    interface MessagePresenter {
        fun attachView(view: MessageView)
        fun fetchData(authToken: AuthToken)
        fun onDestroy()
    }
}