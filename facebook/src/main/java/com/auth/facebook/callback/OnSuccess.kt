package com.auth.facebook.callback

interface OnSuccess<RESULT> {
    fun onSuccess(result: RESULT)
}