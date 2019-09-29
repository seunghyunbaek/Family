package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.WriteRecordContract
import org.json.JSONObject

class WriteRecordModel {

    private var mOnListener: WriteRecordContract.onRecordListener

    constructor(mOnListener:WriteRecordContract.onRecordListener){
        this.mOnListener = mOnListener
    }

    fun saveRecordInServer(context: Context, contentValues: ContentValues, uriList: MutableList<String>?) {
        val url = context.getString(R.string.url) + "record/"
        val cv = contentValues
        val user = DBHelper(context).getUser()
        cv.put("email", user?.email)
        cv.put("name", user?.name)
        cv.put("room", user?.room)
        val json = APIUtils.makeJson(contentValues)

        APIUtils.postWRAsyncTask(mOnListener, context, uriList).execute(url, json)
    }

    fun saveImageInServer(context: Context, str:String, uriList: MutableList<String>?) {
        val jsonObject = JSONObject(str)
        val id:Int = jsonObject.getInt("id")
        val room:Int = jsonObject.getInt("room")

//        val contentValues = ContentValues()
//        contentValues.put("record", id)
//        contentValues.put("room", room)

//        val json = APIUtils.makeJson(contentValues)

        val url = context.getString(R.string.url) + "image/"
        APIUtils.postImageAsyncTask(mOnListener, context, uriList).execute(url, str)
    }
}