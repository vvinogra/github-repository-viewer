package com.github.vvinogra.githubrepositoryviewer.ui.repohistory

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.vvinogra.githubrepositoryviewer.R
import com.github.vvinogra.githubrepositoryviewer.databinding.ActivityRepoHistoryBinding
import com.github.vvinogra.githubrepositoryviewer.di.viewmodel.DaggerViewModelFactory
import com.github.vvinogra.githubrepositoryviewer.ui.adapter.RepositoryListAdapter
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
        setSupportActionBar(binding.toolbar.root).also {
            supportActionBar?.run {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
        setTitle(R.string.repo_history_activity_title)

        binding.repoRecyclerView.run {
            adapter = repositoryListAdapter
            layoutManager = LinearLayoutManager(context)

            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        subscribeEvents()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun subscribeEvents() {
        repoHistoryViewModel.repositoryList.observe(this) {
            binding.emptyRepoHistory.root.visibleIf(it.isEmpty())
            binding.repoRecyclerView.visibleIf(it.isNotEmpty())

            repositoryListAdapter.submitList(it)
        }
    }
}