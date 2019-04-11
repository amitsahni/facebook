package com.fb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.facebook.FacebookConnect
import com.facebook.FacebookSdk
import com.facebook.internal.CallbackManagerImpl
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    internal var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FacebookConnect.getKeyHash(this);
        //        FacebookConfiguration.isDebug(true).config(getApplication());
        //        TwitterConfiguration.keys(TWITTER_KEY, TWITTER_SECRET)
        //                .isDebug(true)
        //                .config(this);


        googleFb.setOnClickListener {
            val user = FacebookConnect.user
            if (user == null) {
                FacebookConnect.with()
                    .login(this)
                    .success {
                        FacebookConnect.with()
                            .profile(this@MainActivity)
                            .success {
                                Log.i(
                                    localClassName,
                                    displayName + " " + email + "" + phoneNumber
                                )
                                Unit
                            }.build()
                    }
                    .error {
                    }.build()
            } else {
                Log.i(localClassName + "Facebook", user.displayName + " " + user.email + "" + user.phoneNumber)
            }
        }


    }

    private fun profile() {
        val map = LinkedHashMap<String, String>()
        // map.put(WebEndPoint.USER_ID_KEY, "1549869761707679");
        /*FbConnect.with()
                .profile(this)
                .success(profileResult -> {
                    Log.i(getLocalClassName(), "Email = " + profileResult.getEmail());
                    StringBuilder builder = new StringBuilder();
                    builder.append("Name = " + profileResult.getName() + "\n" + "\n");
                    builder.append("FirstName LastName = " + profileResult.getFirstName() + " " + profileResult.getLastName() + "\n" + "\n");
                    builder.append("Email = " + profileResult.getEmail() + "\n" + "\n");
                    if (profileResult.getPicture() != null && profileResult.getPicture().getData() != null) {
                        builder.append("Image = " + profileResult.getPicture().getData().getProfileImage() + "\n" + "\n");
                    }
                    builder.append("CustomImage = " + "http://graph.facebook.com/" + profileResult.getId() + "/picture?type=square" + "\n" + "\n");
                    builder.append("CustomImage = " + "http://graph.facebook.com/" + profileResult.getId() + "/picture?type=large");
                    textView.setText(builder.toString());
                    return Unit.INSTANCE;
                })
                .error(error -> {
                    return Unit.INSTANCE;
                })
                .build();*/


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (FacebookSdk.isFacebookRequestCode(requestCode)) {
                //Facebook activity result
                //Do your stuff here
                //Further you can also check if it's login or Share etc by using
                //CallbackManagerImpl as explained by rajath's answer here
                if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
                    //login
                    Log.i(localClassName, "Login")
                    FacebookConnect.onActivityResult(requestCode, resultCode, data!!)
                }
            }
        }

    }


}
