package com.facebook

import android.app.Activity
import android.content.Context
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.callback.OnCancel
import com.facebook.callback.OnError
import com.facebook.callback.OnSuccess
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.UserInfo

class RequestBuilder {

    class LoginBuilder(private val activity: Activity) {
        private var permissions: List<String> = listOf("public_profile", "user_friends", "email")
        private var successCallback: OnSuccess<LoginResult>? = null
        private var errorCallback: OnError<Exception>? = null
        private var cancelCallback: OnCancel? = null

        fun success(success: LoginResult.() -> Unit): LoginBuilder {
            successCallback = object : OnSuccess<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    success(result)
                }
            }
            return this
        }

        fun error(e: Exception.() -> Unit): LoginBuilder {
            errorCallback = object : OnError<Exception> {
                override fun onError(error: Exception) {
                    e(error)
                }
            }
            return this
        }

        fun cancel(cancel: () -> Unit): LoginBuilder {
            cancelCallback = object : OnCancel {
                override fun onCancel() {
                    cancel()
                }
            }
            return this
        }

        fun permissions(permissions: List<String>): LoginBuilder {
            this.permissions = permissions
            return this
        }


        fun build() {
            LoginManager.getInstance()
                    .logInWithReadPermissions(activity, permissions)
            LoginManager.getInstance().registerCallback(FacebookConnect.callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    AccessToken.setCurrentAccessToken(loginResult.accessToken)
                    successCallback?.onSuccess(loginResult)
                }

                override fun onCancel() {
                    cancelCallback?.onCancel()
                }

                override fun onError(e: FacebookException) {
                    errorCallback?.onError(e)
                }
            })
        }
    }


    class ProfileBuilder(val context: Context) {
        private var successCallback: OnSuccess<UserInfo>? = null
        private var errorCallback: OnError<Exception>? = null

        fun success(success: UserInfo.() -> Unit): ProfileBuilder {
            successCallback = object : OnSuccess<UserInfo> {
                override fun onSuccess(result: UserInfo) {
                    success(result)
                }
            }
            return this
        }

        fun error(e: Exception.() -> Unit): ProfileBuilder {
            errorCallback = object : OnError<Exception> {
                override fun onError(error: Exception) {
                    e(error)
                }
            }
            return this
        }

        fun build() {
            val credential = FacebookAuthProvider.getCredential(FacebookConnect.token)
            FacebookConnect.auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            FacebookConnect.auth.currentUser?.let {
                                it.providerData.forEach { user ->
                                    if (user.providerId.equals("facebook.com")) {
                                        successCallback?.onSuccess(user)
                                    }
                                }
                            }

                        } else {
                            it.exception?.let {
                                errorCallback?.onError(it)
                            }
                        }
                    }.addOnFailureListener {
                        errorCallback?.onError(it)
                    }
        }
    }

}