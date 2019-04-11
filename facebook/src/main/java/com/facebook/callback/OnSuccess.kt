package com.facebook.callback

interface OnSuccess<RESULT> {
    fun onSuccess(result: RESULT)
}