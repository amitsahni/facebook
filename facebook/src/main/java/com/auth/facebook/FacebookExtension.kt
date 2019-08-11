package com.auth.facebook

import android.app.Activity
import com.facebook.AccessToken
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.UserInfo

fun Activity.login(
    s: (LoginResult) -> Unit,
    e: (Exception) -> Unit,
    cancel: () -> Unit,
    permissions: List<String> = emptyList()
) {
    if (permissions.isEmpty()) {
        permissions.toMutableList().addAll(listOf("public_profile", "user_friends", "email"))
    }
    LoginManager.getInstance()
        .logInWithReadPermissions(this, permissions)
    LoginManager.getInstance().registerCallback(FacebookManager.callbackManager, object :
        FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {
            AccessToken.setCurrentAccessToken(loginResult.accessToken)
            s(loginResult)
        }

        override fun onCancel() {
            cancel()
        }

        override fun onError(e: FacebookException) {
            e(e)
        }
    })
}

fun profile(
    s: (UserInfo) -> Unit,
    e: (Exception) -> Unit
) {
    val credential = FacebookAuthProvider.getCredential(FacebookManager.token)
    FacebookManager.auth.signInWithCredential(credential)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                FacebookManager.auth.currentUser?.let {
                    it.providerData.forEach { user ->
                        if (user.providerId.equals("facebook.com")) {
                            s(user)
                        }
                    }
                }

            } else {
                it.exception?.let {
                    e(it)
                }
            }
        }.addOnFailureListener {
            e(it)
        }
}