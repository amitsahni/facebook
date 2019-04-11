package com.facebook

import android.app.Activity
import android.content.Context

class Builder {

    fun login(activity: Activity): RequestBuilder.LoginBuilder {
        return RequestBuilder.LoginBuilder(activity)
    }


    fun profile(context: Context): RequestBuilder.ProfileBuilder {
        return RequestBuilder.ProfileBuilder(context)
    }

    fun logOut() {
        FacebookConnect.auth.signOut()
    }
}