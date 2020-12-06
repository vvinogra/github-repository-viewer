package com.github.vvinogra.githubrepositoryviewer.data.network

object NetworkConstants {
    const val BASE_GITHUB_API_URL = "https://api.github.com"
    const val BASE_GITHUB_URL = "https://github.com"

    const val APP_CLIENT_ID = "099e108b775ada518af9"
    const val APP_CLIENT_SECRET = "2cbe08d6c7d4fd08441f2aafac413038c35e1b0b"

    const val GITHUB_API_VERSION_HEADER = "application/vnd.github.v3+json"
    const val ACCEPT_HEADER = "Accept"
    const val AUTHORIZATION_HEADER = "Authorization"

    object IdentificationApi {
        const val PATH = "login/oauth/authorize"

        object Params {
            const val CLIENT_ID = "client_id"
        }
    }

    object AuthorizationApi {
        const val PATH = "login/oauth/access_token"
        const val DEFAULT_ACCEPT_HEADER = "application/json"

        object Params {
            const val CLIENT_ID = "client_id"
            const val CLIENT_SECRET = "client_secret"
            const val CODE = "code"
        }
    }

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
            const val HELP_WANTED = "help-wanted-issues"
        }
    }
}