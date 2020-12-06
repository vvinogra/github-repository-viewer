package com.github.vvinogra.githubrepositoryviewer.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.vvinogra.githubrepositoryviewer.R
import com.github.vvinogra.githubrepositoryviewer.data.network.NetworkConstants
import com.github.vvinogra.githubrepositoryviewer.databinding.ActivityLoginBinding
import com.github.vvinogra.githubrepositoryviewer.di.viewmodel.DaggerViewModelFactory
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.SearchRepoActivity
import com.github.vvinogra.githubrepositoryviewer.ui.utils.visibleIf
import dagger.android.AndroidInjection
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    private val loginViewModel: LoginViewModel by viewModels { daggerViewModelFactory }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setTitle(R.string.login_activity_title)

        binding.signInBtn.setOnClickListener {
            openWebLoginPage()
        }

        subscribeEvents()

        if (loginViewModel.isLoggedIn) {
            onUserLoggedIn()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.data?.getQueryParameter("code")?.let {
            loginViewModel.loginUser(it)
        }
    }

    private fun onUserLoggedIn() {
        val intent = Intent(this, SearchRepoActivity::class.java)

        startActivity(intent)
        finish()
    }

    private fun subscribeEvents() {
        loginViewModel.loading.observe(this) {
            binding.signInProgressBar.visibleIf(it)
            binding.signInBtn.run {
                text = if (it) "" else getString(R.string.sign_in_btn_text)
                isClickable = !it
            }
        }
    }

    private fun openWebLoginPage() {
        val uri = Uri.parse(NetworkConstants.BASE_GITHUB_URL)
            .buildUpon()
            .appendEncodedPath(NetworkConstants.IdentificationApi.PATH)
            .appendQueryParameter(
                NetworkConstants.IdentificationApi.Params.CLIENT_ID,
                NetworkConstants.APP_CLIENT_ID
            ).build()

        val intent = Intent(Intent.ACTION_VIEW, uri)

        startActivity(intent)
    }
}