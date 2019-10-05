package com.hyun.familyapplication.model

import android.content.Context
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.FindContract

class FindModel {
    companion object {
        fun findUser(context: Context, email:String, listener:FindContract.Listener) {
            val url = context.getString(R.string.url) + "user/" + email.replace(".","_") + "/"
            APIUtils.getFindAsyncTask(listener).execute(url)
        }
    }
}