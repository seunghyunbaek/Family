package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.WriteMessageContract

class WriteMessageModel {
    companion object {
        fun sendMessage(context: Context, receiver:String, content:String, listener:WriteMessageContract.Listener) {
            val db = DBHelper(context)
            val user = db.getUser()

            val cv = ContentValues()
            cv.put("receiver", receiver)
            cv.put("content", content)
            cv.put("sender", user!!.email)

            val json = APIUtils.makeJson(cv)
            val url = context.getString(R.string.url) + "message/"
            APIUtils.postMessageAsyncTask(listener).execute(url, json)
        }
    }
}