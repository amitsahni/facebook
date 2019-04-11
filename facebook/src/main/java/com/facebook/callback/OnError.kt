package com.facebook.callback

interface OnError<ERROR> {
    fun onError(error: ERROR)
}