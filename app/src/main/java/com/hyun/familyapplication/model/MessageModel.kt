package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.MessageContract
import org.json.JSONArray

class MessageModel {
    companion object {
        fun getData(context: Context, listener: MessageContract.Listener) {
            val db = DBHelper(context)
            val user = db.getUser()
            val url = context.getString(R.string.url) + "message/?receiver=${user?.email}"

            APIUtils.getMessageAsyncTask(listener).execute(url)
        }

        fun readMessage(context:Context, listener: MessageContract.Listener) {
            val db = DBHelper(context)
            val user = db.getUser()
            val url = context.getString(R.string.url) + "messagereceive/" + user?.email + "/"

            val cv = ContentValues()
            cv.put("receiver", user?.email)
            cv.put("count", 0)
            val json = APIUtils.makeJson(cv)

            // put messagereceive
            APIUtils.putMessageReceive2AsyncTask(listener).execute(url, json)
        }

        fun parseArray(result:String):ArrayList<Message> {
            val jsonArray = JSONArray(result)
            val list = ArrayList<Message>()

            var cnt = 0
            while(cnt < jsonArray.length()) {
                val json = jsonArray.getJSONObject(cnt)
                val message = Message()
                message.id = json.getInt("id")
                message.sender = json.getString("sender")
                message.receiver = json.getString("receiver")
                message.content = json.getString("content")

                list.add(message)
                cnt++
            }

            return list
        }
    }
}