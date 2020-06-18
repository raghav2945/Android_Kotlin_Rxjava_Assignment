package com.example.androidassignment.presenter

import com.example.androidassignment.views.activities.LoginActivity.Companion.AUTH_TOKEN
import com.example.androidassignment.converter.MessageConverter
import com.example.androidassignment.model.AuthToken
import com.example.androidassignment.model.ThreadDetails
import com.example.androidassignment.network.APIClient
import com.example.androidassignment.preferences.ModelPreferencesManager
import com.example.androidassignment.views.DetailViewPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailPresenter : DetailViewPresenter.DetailPresenter {

    private lateinit var view: DetailViewPresenter.DetailView

    private var disposable: Disposable? = null

    override fun attachView(view: DetailViewPresenter.DetailView) {
        this.view = view
        view.init()
    }

    override fun fetchData(threadDetails: ThreadDetails) {
        if (view.isInternetConnected()) {
            val authToken = ModelPreferencesManager.get<AuthToken>(AUTH_TOKEN)
            authToken?.let {
                disposable = APIClient.instance.getMessage(it.auth_token, threadDetails)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { view.loading() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        view.updateData(MessageConverter.convertDtoToModel(it))
                    }, {
                        view.onError()
                    })
            }

        } else {
            view.noInternet()
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

}