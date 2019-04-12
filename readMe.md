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
FacebookConnect.getKeyHash(this)
```
`Login Sample`

```kotlin
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
```


`Profile Sample`

```kotlin
       FbConnect.with()
                .profile(this)
                .otherUserId("")
                .success { 
                    
                }.error { 
                    
                }.build()
```

`OnActivityResult`

```kotlin
if (resultCode == Activity.RESULT_OK) {
            if (FacebookSdk.isFacebookRequestCode(requestCode)) {
                if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
                    FbConnect.get().onActivityResult(requestCode, resultCode, data);
                }
            }
        }
```


```groovy
repositories {
    maven {
        url  "https://dl.bintray.com/amitsahni/Library" 
    }
}
```

```groovy
implementation 'com.amitsahni:facebook:0.0.1-alpha03'
```