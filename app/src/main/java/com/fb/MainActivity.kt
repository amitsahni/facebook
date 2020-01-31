package com.fb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.auth.facebook.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    internal var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("HashKey = ", getKeyHash)
        googleFb.setOnClickListener {
            val user = user
            if (user == null) {
                fbLogin { userInfo, e, cancel ->
                    userInfo?.let {
                        Log.i(
                                localClassName,
                                it.accessToken.token
                        )
                        fbProfile { userInfo, exception ->
                            userInfo?.let {
                                Log.i(
                                        localClassName,
                                        it.displayName + " " + it.email + "" + it.phoneNumber
                                )
                            }

                            exception?.let {
                                Log.e(
                                        localClassName,
                                        it.message
                                )
                            }
                        }
                    }
                    e?.let {
                        Log.i(
                                localClassName,
                                it.message
                        )
                    }
                    cancel?.let {

                    }
                }
            } else {
                Log.i(localClassName + "Facebook", user.displayName + " " + user.email + "" + user.phoneNumber)
                fbLogout()
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onFBActivityResult(requestCode, resultCode, data!!)
    }


}
