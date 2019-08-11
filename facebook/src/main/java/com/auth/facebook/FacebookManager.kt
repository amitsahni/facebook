package com.auth.facebook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.facebook.internal.CallbackManagerImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


object FacebookManager {
    @JvmStatic
    val callbackManager = CallbackManager.Factory.create()

    @JvmStatic
    internal val auth = FirebaseAuth.getInstance()

    @JvmStatic
    fun getKeyHash(context: Context) {
        try {
            val info = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.i(javaClass.getSimpleName(), "KeyHash:" + Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    val accessToken = AccessToken.getCurrentAccessToken()

    @JvmStatic
    val token: String
        get() {
            AccessToken.getCurrentAccessToken()?.let {
                return it.token
            }
            return ""
        }

    @JvmStatic
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
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

    @JvmStatic
    val user: UserInfo?
        get() {
            FirebaseAuth.getInstance().currentUser?.let {
                it.providerData.forEach {
                    if (it.providerId == "facebook.com") {
                        return it
                    }
                }
            }
            return null
        }

}