package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.WriteMessageContract
import org.json.JSONObject

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
            APIUtils.postMessageAsyncTask(context, listener).execute(url, json)
        }

        fun getMessageCount(context: Context, result:String, listener:WriteMessageContract.Listener) {
            val db = DBHelper(context)
            val user = db.getUser()
            val jsonObject = JSONObject(result)
            val receiver = jsonObject.getString("receiver")
            val url = context.getString(R.string.url) + "messagereceive/" + receiver + "/"

            APIUtils.getMessageCountAsyncTask(context, listener, receiver).execute(url)
        }

        fun createMessageReceive(context: Context, listener: WriteMessageContract.Listener, receiver: String) {
            val db = DBHelper(context)
            val user = db.getUser()
            val url = context.getString(R.string.url) + "messagereceive/"

            val cv = ContentValues()
            cv.put("receiver", receiver)
            cv.put("count", 1)

            val json = APIUtils.makeJson(cv)

            APIUtils.postMessageReceiveAsyncTask(context, listener).execute(url, json)
        }

        fun addMessageCount(context: Context, result:String, listener:WriteMessageContract.Listener) {
            val db = DBHelper(context)
            val user = db.getUser()

            val jsonObject = JSONObject(result)
            val receiver = jsonObject.getString("receiver")
            var count = jsonObject.getInt("count")

            val cv = ContentValues()
            cv.put("receiver", receiver)
            cv.put("count", count+1)

            val json = APIUtils.makeJson(cv)
//            val url = context.getString(R.string.url) + "messagereceive/" + user?.email + "/"
            val url = context.getString(R.string.url) + "messagereceive/" + receiver + "/"

            APIUtils.putMessageReceiveAsyncTask(listener).execute(url, json)
        }
    }
}