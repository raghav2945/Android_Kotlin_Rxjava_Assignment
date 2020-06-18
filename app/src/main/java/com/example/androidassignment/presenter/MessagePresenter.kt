package com.example.androidassignment.presenter

import com.example.androidassignment.views.activities.LoginActivity
import com.example.androidassignment.views.activities.LoginActivity.Companion.AUTH_TOKEN
import com.example.androidassignment.converter.MessageConverter
import com.example.androidassignment.model.AuthToken
import com.example.androidassignment.network.APIClient
import com.example.androidassignment.preferences.ModelPreferencesManager
import com.example.androidassignment.views.MessageViewPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MessagePresenter : MessageViewPresenter.MessagePresenter {

    lateinit var view: MessageViewPresenter.MessageView
    private var disposable: Disposable? = null

    override fun attachView(view: MessageViewPresenter.MessageView) {
        this.view = view
        checkForLogin()
    }

    override fun fetchData(authToken: AuthToken) {
        if (view.isInternetConnected()){
            disposable = APIClient.instance.getMessages(authToken.auth_token)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { view.loading() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.updateData(MessageConverter.convertDtoToModel(it))
                }, {
                    view.onError()
                })
        } else {
            view.noInternet()
        }
    }

    private fun checkForLogin(){
        if (ModelPreferencesManager.preferences.getBoolean(LoginActivity.IS_LOGGED_IN, false).not()){
            view.launchLogin()
        } else {
            val authToken = ModelPreferencesManager.get<AuthToken>(AUTH_TOKEN)
            view.initViews(authToken)
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

}