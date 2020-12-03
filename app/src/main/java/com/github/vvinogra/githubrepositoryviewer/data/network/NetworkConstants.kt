package com.github.vvinogra.githubrepositoryviewer.data.network

object NetworkConstants {
    const val GITHUB_API_VERSION_HEADER = "application/vnd.github.v3+json"

    object SearchApi {
        const val path = "search/repositories"

        object Params {
            const val query = "q"
            const val sort = "sort"
            const val page = "page"
            const val perPage = "per_page"
        }

        object SortByOption {
            const val STARS = "stars"
            const val FORKS = "forks"
            const val HELP_WANTED ="help-wanted-issues"
        }
    }
}