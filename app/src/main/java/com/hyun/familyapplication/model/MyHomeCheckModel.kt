package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.MyHomeCheckContract
import org.json.JSONObject

class MyHomeCheckModel {

    private var mOnListener: MyHomeCheckContract.onMyHomeCheckListener

    constructor(mOnListener: MyHomeCheckContract.onMyHomeCheckListener) {
        this.mOnListener = mOnListener
    }

    fun createRoomInServer(context: Context) {
        val url = context.getString(R.string.url) + "room/"
        val contentValues = ContentValues()
        val dbHelper = DBHelper(context)
        val email = dbHelper.getUser()?.email
        contentValues.put("email", email)
        val json = APIUtils.makeJson(contentValues)
        APIUtils.postRoomAsyncTask(mOnListener, context).execute(url, json)
    }

    fun updateUser(context:Context, str:String) {
        val jsonObject = JSONObject(str)
        val user = DBHelper(context).getUser()
        val contentValues = ContentValues()
        val url = context.getString(R.string.url) + "user/" + user?.email + "/"
        contentValues.put("email", user?.email)
        contentValues.put("name", user?.name)
        contentValues.put("room", jsonObject.getString("id").toInt())
        val json = APIUtils.makeJson(contentValues)
        APIUtils.putRoomAsyncTask(mOnListener, context).execute(url, json)
    }
}