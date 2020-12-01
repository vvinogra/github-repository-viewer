package com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.vvinogra.githubrepositoryviewer.databinding.LiRepoBinding
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.presentation.RepositoryPresentation
import com.github.vvinogra.githubviewer.data.network.model.Repository
import com.squareup.picasso.Picasso
import javax.inject.Inject


class RepositoryListAdapter @Inject constructor(
    private val picasso: Picasso
): PagedListAdapter<RepositoryPresentation, RepositoryListAdapter.RepositoryListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListViewHolder {
        val binding = LiRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RepositoryListViewHolder(binding, picasso)
    }

    override fun onBindViewHolder(holder: RepositoryListViewHolder, position: Int) {
        val item = getItem(position)

        item?.let {
            holder.bind(it)
        }
    }

    class RepositoryListViewHolder(
        private val repoBinding: LiRepoBinding,
        private val picasso: Picasso
    ) : RecyclerView.ViewHolder(repoBinding.root) {

        fun bind(item: RepositoryPresentation) {
            item.run {
                repoBinding.repoName.text = item.repositoryName
                repoBinding.ownerName.text = item.ownerName
                repoBinding.repoDescription.text = item.description
                repoBinding.stargazerCount.text = item.stargazers.toString()

                picasso.load(item.ownerAvatarUrl)
                    .fit()
                    .into(repoBinding.ownerAvatar)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RepositoryPresentation>() {
            override fun areItemsTheSame(
                oldConcert: RepositoryPresentation,
                newConcert: RepositoryPresentation
            ) = oldConcert.id == newConcert.id

            override fun areContentsTheSame(
                oldConcert: RepositoryPresentation,
                newConcert: RepositoryPresentation
            ) = oldConcert == newConcert
        }
    }
}