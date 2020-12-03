package com.github.vvinogra.githubrepositoryviewer.data.network

object NetworkConstants {
    const val GITHUB_API_VERSION_HEADER = "application/vnd.github.v3+json"

    object SearchApi {
        const val PATH = "search/repositories"

        object Params {
            const val QUERY = "q"
            const val SORT = "sort"
            const val PAGE = "page"
            const val PER_PAGE = "per_page"
        }

        object SortByOption {
            const val STARS = "stars"
            const val FORKS = "forks"
            const val HELP_WANTED ="help-wanted-issues"
        }
    }
}