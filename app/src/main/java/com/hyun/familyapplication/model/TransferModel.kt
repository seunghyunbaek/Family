package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.TransferContract

class TransferModel {
    companion object {
        fun selectUser(context:Context, listener: TransferContract.Listener, user:User) {
            val contentValues = ContentValues()
            contentValues.put("id", user.room)
            contentValues.put("email", user.email)
            val json = APIUtils.makeJson(contentValues)
            val url = context.getString(R.string.url) + "room/" + user.room + "/"
            APIUtils.putTransferAsyncTask(context, listener).execute(url, json)
        }

        fun changeUserSQLite(context: Context, result:String):Int {
            val db = DBHelper(context)
            val user = db.getUser()!!
            val cv = ContentValues()
            cv.put("email", user.email)
            cv.put("name", user.name)
            cv.put("hoching", user.hoching)
            cv.put("gender", user.gender)
            cv.put("phone", user.phone)
            cv.put("anniversary", user.anniversary)
            cv.put("room", 0)

            val r = db.updateUserRoom(cv)
            return r
        }
    }
}