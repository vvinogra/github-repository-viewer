package com.github.vvinogra.githubrepositoryviewer.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.vvinogra.githubrepositoryviewer.data.utils.RxSchedulers
import com.github.vvinogra.githubrepositoryviewer.domain.authorization.AuthorizationDomain
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authorizationDomain: AuthorizationDomain,
    private val rxSchedulers: RxSchedulers
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val isLoggedIn: Boolean
        get() = authorizationDomain.isLoggedIn

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    fun loginUser(temporaryCode: String) {
        compositeDisposable.add(authorizationDomain.authorizeUser(temporaryCode)
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.main())
            .doOnSubscribe {
                _loading.value = true
            }
            .subscribe({}, {
                _loading.value = false
            })
        )
    }
}