package com.hyun.familyapplication.model

import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.MainContract
import org.json.JSONObject

class MainModel {
    companion object {
        fun checkMessage(context: Context, listener:MainContract.Listener) {
            val db = DBHelper(context)
            val user = db.getUser()
            val url = context.getString(R.string.url) + "messagereceive/" + user?.email + "/"

            APIUtils.getCheckMessageAsyncTask(listener).execute(url)
        }


        fun getCount(result:String):Int {
            val json = JSONObject(result)
            val count = json.getInt("count")

            return count
        }
    }
}