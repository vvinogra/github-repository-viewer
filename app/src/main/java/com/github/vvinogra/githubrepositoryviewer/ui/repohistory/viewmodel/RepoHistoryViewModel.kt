package com.github.vvinogra.githubrepositoryviewer.ui.repohistory.viewmodel

import androidx.lifecycle.ViewModel
import com.github.vvinogra.githubrepositoryviewer.ui.domain.RepositoryDomain
import javax.inject.Inject

class RepoHistoryViewModel @Inject constructor(
    private val repositoryDomain: RepositoryDomain
): ViewModel() {
    val repositoryList = repositoryDomain.loadRepositoryHistory()
}