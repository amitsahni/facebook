[ ![Download](https://api.bintray.com/packages/amitsahni/Library/facebook/images/download.svg) ](https://bintray.com/amitsahni/Library/facebook/_latestVersion)

----
#### have to set in AndroidManifest
```xml
<!-- Facebook Integration -->
        <activity
            android:name="com.facebook.FacebookActivity"
            android:configChanges="keyboard|keyboardHidden|screenLayout|screenSize|orientation"
            android:label="@string/app_name"
            android:screenOrientation="portrait" />

        <meta-data
            android:name="com.facebook.sdk.ApplicationId"
            android:value="@string/fb_app_id" />
        <meta-data
            android:name="com.facebook.sdk.ApplicationName"
            android:value="@string/app_name" />
```
`Sample` Example

To get `KeyHash`

```
FacebookManager.getKeyHash(this)
```
`Login Sample`

```kotlin
val user = FacebookManager.user
            if (user == null) {
                login({
                    com.auth.facebook.profile({
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
```


`Profile Sample`

```kotlin
profile({
                        Log.i(
                            localClassName,
                            it.displayName + " " + it.email + "" + it.phoneNumber
                        )
                    }, {
                        it.printStackTrace()
                    })
```

`OnActivityResult`

```kotlin
FacebookManager.onActivityResult(requestCode, resultCode, data!!)
```


```groovy
repositories {
    maven {
        url  "https://dl.bintray.com/amitsahni/Library" 
    }
}
```

```groovy
implementation 'com.amitsahni:facebook:0.0.1-alpha05'
```