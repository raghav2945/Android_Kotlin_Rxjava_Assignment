package com.example.androidassignment.presenter

import com.example.androidassignment.views.activities.LoginActivity.Companion.AUTH_TOKEN
import com.example.androidassignment.views.activities.LoginActivity.Companion.IS_LOGGED_IN
import com.example.androidassignment.model.LoginDetails
import com.example.androidassignment.network.APIClient
import com.example.androidassignment.preferences.ModelPreferencesManager
import com.example.androidassignment.views.LoginViewPresenter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter : LoginViewPresenter.LoginPresenter {

    lateinit var view: LoginViewPresenter.LoginView
    private var disposable: Disposable? = null

    override fun attachView(view: LoginViewPresenter.LoginView) {
        this.view = view
        view.initViews()
    }

    override fun fetchData() {
        if (view.isInternetConnected()) {
            val loginDetails = LoginDetails(view.username(), view.password())
            disposable = APIClient.instance.login(loginDetails)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { view.loading() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    ModelPreferencesManager.put(it, AUTH_TOKEN)
                    ModelPreferencesManager.preferences.edit().putBoolean(IS_LOGGED_IN, true)
                        .apply()
                    view.onSuccessfulLogin()
                }, {
                    val statusCode = (it as HttpException).code()
                    view.onError(statusCode)
                })
        } else {
            view.noInternet()
        }

    }

    override fun isValidInput(): Boolean =
        view.username().isNotEmpty() && view.password().isNotEmpty()

    override fun onDestroy() {
        disposable?.dispose()
    }

}