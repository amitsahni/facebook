package com.auth.facebook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import com.facebook.*
import com.facebook.internal.CallbackManagerImpl
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

val callbackManager = CallbackManager.Factory.create()
val facebookAuth = FirebaseAuth.getInstance()
val accessToken = AccessToken.getCurrentAccessToken()
val facebookUser = facebookAuth.currentUser
val token: String
    get() {
        AccessToken.getCurrentAccessToken()?.let {
            return it.token
        }
        return ""
    }

val user: UserInfo?
    get() {
        facebookUser?.let {
            it.providerData.forEach {
                if (it.providerId == "facebook.com") {
                    return it
                }
            }
        }
        return null
    }

val Context.getKeyHash: String?
    get() {
        try {
            val info = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hash = Base64.encodeToString(md.digest(), Base64.DEFAULT)
                Log.d(javaClass.simpleName, "KeyHash:$hash")
                return hash
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return null
    }


fun Activity.fbLogin(
        permissions: List<String> = emptyList(),
        f: (LoginResult?, Exception?, Unit?) -> Unit
) {
    if (permissions.isEmpty()) {
        permissions.toMutableList().addAll(listOf("public_profile", "user_friends", "email"))
    }
    LoginManager.getInstance()
            .logInWithReadPermissions(this, permissions)
    LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {
            AccessToken.setCurrentAccessToken(loginResult.accessToken)
            f(loginResult, null, null)
        }

        override fun onCancel() {
            f(null, null, Unit)
        }

        override fun onError(e: FacebookException) {
            f(null, e, null)
        }
    })
}

fun onFBActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    if (resultCode == Activity.RESULT_OK) {
        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            //Facebook activity result
            //Do your stuff here
            //Further you can also check if it's login or Share etc by using
            if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
                //login
                callbackManager?.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}

fun fbProfile(f: (UserInfo?, Exception?) -> Unit) {
    val credential = FacebookAuthProvider.getCredential(token)
    facebookAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    facebookUser?.let {
                        it.providerData.forEach { user ->
                            if (user.providerId.equals("facebook.com")) {
                                f(user, null)
                            }
                        }
                    }

                } else {
                    f(null, it.exception)
                }
            }.addOnFailureListener {
                f(null, it)
            }
}

fun fbLogout() {
    facebookAuth.signOut()
}