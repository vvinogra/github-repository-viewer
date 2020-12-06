package com.github.vvinogra.githubrepositoryviewer.ui.searchrepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.github.vvinogra.githubrepositoryviewer.data.utils.RxSchedulers
import com.github.vvinogra.githubrepositoryviewer.domain.authorization.AuthorizationDomain
import com.github.vvinogra.githubrepositoryviewer.domain.repository.Listing
import com.github.vvinogra.githubrepositoryviewer.domain.repository.NetworkState
import com.github.vvinogra.githubrepositoryviewer.domain.repository.RepositoryDomain
import com.github.vvinogra.githubrepositoryviewer.domain.repository.Repository
import com.github.vvinogra.githubrepositoryviewer.ui.utils.Event
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class SearchRepoViewModel @Inject constructor(
    private val repositoryDomain: RepositoryDomain,
    private val authorizationDomain: AuthorizationDomain,
    private val rxSchedulers: RxSchedulers
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val queryText = MutableLiveData<String>()

    private val itemListing: LiveData<Listing<Repository>> =
        Transformations.map(queryText) {
            repositoryDomain.searchRepositories(it)
        }

    val repositories: LiveData<PagedList<Repository>> =
        Transformations.switchMap(itemListing) {
            it.pagedList
        }

    val refreshState: LiveData<NetworkState> = Transformations.switchMap(itemListing) {
        it.refreshState
    }

    val networkState: LiveData<NetworkState> = Transformations.switchMap(itemListing) {
        it.networkState
    }

    private val _itemSelected = MutableLiveData<Event<Repository>>()
    val itemSelectedEvent: LiveData<Event<Repository>> = _itemSelected

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    fun logout() {
        authorizationDomain.logoutUser()
    }

    fun selectItem(item: Repository) {
        compositeDisposable.add(
            repositoryDomain.addItemToHistory(item)
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.main())
                .subscribe({}, {})
        )

        _itemSelected.value = Event(item)
    }

    fun refresh() {
        itemListing.value?.let {
            it.refresh()
        }
    }

    fun showSearchResults(searchQuery: String): Boolean {
        if (queryText.value == searchQuery) {
            return false
        }

        queryText.value = searchQuery
        return true
    }
}