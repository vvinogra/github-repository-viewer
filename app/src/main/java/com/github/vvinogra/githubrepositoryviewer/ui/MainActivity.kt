package com.github.vvinogra.githubrepositoryviewer.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.github.vvinogra.githubrepositoryviewer.R
import com.github.vvinogra.githubrepositoryviewer.di.viewmodel.DaggerViewModelFactory
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject lateinit var daggerViewModelFactory: DaggerViewModelFactory

    private val searchRepoViewModel: ViewModel by viewModels { daggerViewModelFactory }

    val firebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        val provider = OAuthProvider.newBuilder("github.com")

        firebaseAuth
            .startActivityForSignInWithProvider(this, provider.build())
            .addOnSuccessListener {
                // User is signed in.
                // IdP data available in
                // authResult.getAdditionalUserInfo().getProfile().
                // The OAuth access token can also be retrieved:
                // authResult.getCredential().getAccessToken().
            }
            .addOnFailureListener {
                // Handle failure.
            }
    }
}