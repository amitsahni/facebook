package com.fb

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.auth.facebook.FacebookManager
import com.auth.facebook.login
import com.auth.facebook.profile
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    internal var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FacebookManager.getKeyHash(this)
        googleFb.setOnClickListener {
            val user = FacebookManager.user
            if (user == null) {
                login({
                    profile({
                        Log.i(
                            localClassName,
                            it.displayName + " " + it.email + "" + it.phoneNumber
                        )
                    }, {
                        it.printStackTrace()
                    })
                }, {
                    it.printStackTrace()
                }, {
                    Log.i(
                        localClassName,
                        "User Cancelled"
                    )
                })
            } else {
                Log.i(localClassName + "Facebook", user.displayName + " " + user.email + "" + user.phoneNumber)
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        FacebookManager.onActivityResult(requestCode, resultCode, data!!)
    }


}
