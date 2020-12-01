package com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.model.Listing
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.model.NetworkState
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.model.SearchRepoModel
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.presentation.RepositoryPresentation
import com.github.vvinogra.githubviewer.data.network.model.Repository
import javax.inject.Inject

class SearchRepoViewModel @Inject constructor(
    private val searchRepoModel: SearchRepoModel
): ViewModel() {

    private val queryText = MutableLiveData<String>()

    private val itemListing: LiveData<Listing<RepositoryPresentation>> = Transformations.map(queryText) {
        searchRepoModel.searchRepo(it)
    }

    val repositories: LiveData<PagedList<RepositoryPresentation>> = Transformations.switchMap(itemListing) {
        it.pagedList
    }

    val refreshState: LiveData<NetworkState> = Transformations.switchMap(itemListing) {
        it.refreshState
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