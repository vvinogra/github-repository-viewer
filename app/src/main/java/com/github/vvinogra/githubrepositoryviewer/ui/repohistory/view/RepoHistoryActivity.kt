package com.github.vvinogra.githubrepositoryviewer.ui.repohistory.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.vvinogra.githubrepositoryviewer.R
import com.github.vvinogra.githubrepositoryviewer.databinding.ActivityRepoHistoryBinding
import com.github.vvinogra.githubrepositoryviewer.di.viewmodel.DaggerViewModelFactory
import com.github.vvinogra.githubrepositoryviewer.ui.repohistory.viewmodel.RepoHistoryViewModel
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.view.RepositoryListAdapter
import com.github.vvinogra.githubrepositoryviewer.ui.utils.visibleIf
import dagger.android.AndroidInjection
import javax.inject.Inject

class RepoHistoryActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    @Inject
    lateinit var repositoryListAdapter: RepositoryListAdapter

    private val repoHistoryViewModel: RepoHistoryViewModel by viewModels { daggerViewModelFactory }

    private lateinit var binding: ActivityRepoHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        binding = ActivityRepoHistoryBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setTitle(R.string.repo_history_activity_title)

        binding.repoRecyclerView.run {
            adapter = repositoryListAdapter
            layoutManager = LinearLayoutManager(context)

            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        subscribeEvents()
    }

    private fun subscribeEvents() {
        repoHistoryViewModel.repositoryList.observe(this) {
            binding.emptyRepoHistory.root.visibleIf(it.isEmpty())
            binding.repoRecyclerView.visibleIf(it.isNotEmpty())

            repositoryListAdapter.submitList(it)
        }
    }
}