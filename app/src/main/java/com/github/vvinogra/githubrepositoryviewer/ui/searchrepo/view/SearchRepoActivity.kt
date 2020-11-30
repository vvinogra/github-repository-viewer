package com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import com.github.vvinogra.githubrepositoryviewer.R
import com.github.vvinogra.githubrepositoryviewer.di.viewmodel.DaggerViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class SearchRepoActivity : AppCompatActivity() {

    @Inject lateinit var daggerViewModelFactory: DaggerViewModelFactory

    private val searchRepoViewModel: ViewModel by viewModels { daggerViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        setContentView(R.layout.activity_search_repo)

        title = "Search"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_repo_toolbar_menu, menu)

        val searchMenuItem = menu.findItem(R.id.action_search)

        val searchView = searchMenuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
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
}