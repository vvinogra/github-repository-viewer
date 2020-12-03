package com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.github.vvinogra.githubrepositoryviewer.data.utils.RxSchedulers
import com.github.vvinogra.githubrepositoryviewer.ui.domain.Listing
import com.github.vvinogra.githubrepositoryviewer.ui.domain.NetworkState
import com.github.vvinogra.githubrepositoryviewer.ui.domain.RepositoryDomain
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.presentation.RepositoryPresentation
import com.github.vvinogra.githubrepositoryviewer.ui.utils.Event
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class SearchRepoViewModel @Inject constructor(
    private val repositoryDomain: RepositoryDomain,
    private val rxSchedulers: RxSchedulers
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val queryText = MutableLiveData<String>()

    private val itemListing: LiveData<Listing<RepositoryPresentation>> =
        Transformations.map(queryText) {
            repositoryDomain.searchRepositories(it)
        }

    val repositories: LiveData<PagedList<RepositoryPresentation>> =
        Transformations.switchMap(itemListing) {
            it.pagedList
        }

    val refreshState: LiveData<NetworkState> = Transformations.switchMap(itemListing) {
        it.refreshState
    }

    private val _itemSelected = MutableLiveData<Event<RepositoryPresentation>>()
    val itemSelectedEvent: LiveData<Event<RepositoryPresentation>> = _itemSelected

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    fun selectItem(item: RepositoryPresentation) {
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