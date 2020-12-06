package com.github.vvinogra.githubrepositoryviewer.ui.repohistory

import androidx.lifecycle.ViewModel
import com.github.vvinogra.githubrepositoryviewer.domain.repository.RepositoryDomain
import javax.inject.Inject

class RepoHistoryViewModel @Inject constructor(
    private val repositoryDomain: RepositoryDomain
): ViewModel() {
    val repositoryList = repositoryDomain.loadRepositoryHistory()
}