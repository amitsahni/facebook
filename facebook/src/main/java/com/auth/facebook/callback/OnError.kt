package com.auth.facebook.callback

interface OnError<ERROR> {
    fun onError(error: ERROR)
}