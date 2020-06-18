package com.example.androidassignment.views

import com.example.androidassignment.model.Message
import com.example.androidassignment.model.ThreadDetails

interface DetailViewPresenter {

    interface DetailView {
        fun init()
        fun updateData(message: Message)
        fun onError()
        fun isInternetConnected(): Boolean
        fun noInternet()
        fun loading()
    }

    interface DetailPresenter {
        fun attachView(view: DetailView)
        fun fetchData(threadDetails: ThreadDetails)
        fun onDestroy()
    }
}