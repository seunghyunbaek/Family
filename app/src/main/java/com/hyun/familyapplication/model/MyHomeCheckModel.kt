package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.MyHomeCheckContract

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
}