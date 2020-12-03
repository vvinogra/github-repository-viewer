package com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.vvinogra.githubrepositoryviewer.R
import com.github.vvinogra.githubrepositoryviewer.databinding.ActivitySearchRepoBinding
import com.github.vvinogra.githubrepositoryviewer.di.viewmodel.DaggerViewModelFactory
import com.github.vvinogra.githubrepositoryviewer.ui.repohistory.view.RepoHistoryActivity
import com.github.vvinogra.githubrepositoryviewer.ui.domain.NetworkState
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.presentation.RepositoryPresentation
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.viewmodel.SearchRepoViewModel
import com.github.vvinogra.githubrepositoryviewer.ui.utils.EventObserver
import com.github.vvinogra.githubrepositoryviewer.ui.utils.visibleIf
import dagger.android.AndroidInjection
import javax.inject.Inject

class SearchRepoActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    @Inject
    lateinit var repositoryListAdapter: RepositoryListAdapter

    private val searchRepoViewModel: SearchRepoViewModel by viewModels { daggerViewModelFactory }

    private lateinit var binding: ActivitySearchRepoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        binding = ActivitySearchRepoBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setTitle(R.string.search_repo_activity_title)

        repositoryListAdapter.onItemClickListener = {
            searchRepoViewModel.selectItem(it)
        }

        binding.repoRecyclerView.run {
            adapter = repositoryListAdapter
            layoutManager = LinearLayoutManager(context)

            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        binding.swipeRefreshRepoList.setOnRefreshListener {
            searchRepoViewModel.refresh()
        }

        subscribeEvents()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_repo_toolbar_menu, menu)

        val searchMenuItem = menu.findItem(R.id.action_search)

        val searchView = searchMenuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchRepoViewModel.showSearchResults(query)

                if (!searchView.isIconified) {
                    searchView.isIconified = true
                }

                searchMenuItem.collapseActionView()

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                showRepoHistory()
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun subscribeEvents() {
        searchRepoViewModel.repositories.observe(this) {
            binding.noRepositoriesFound.root.visibleIf(it.isEmpty())
            binding.repoRecyclerView.visibleIf(it.isNotEmpty())

            repositoryListAdapter.submitList(it)
        }

        searchRepoViewModel.refreshState.observe(this) {
            binding.swipeRefreshRepoList.isRefreshing = it == NetworkState.LOADING
        }

        searchRepoViewModel.itemSelectedEvent.observe(this, EventObserver {
            openRepository(it)
        })
    }

    private fun showRepoHistory() {
        val historyPageIntent = Intent(this, RepoHistoryActivity::class.java)

        startActivity(historyPageIntent)
    }

    private fun openRepository(repositoryPresentation: RepositoryPresentation) {
        val repoWebUri = Uri.parse(repositoryPresentation.webPageUrl)

        val browserIntent = Intent(Intent.ACTION_VIEW, repoWebUri)

        startActivity(browserIntent)
    }
}