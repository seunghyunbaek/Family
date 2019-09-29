package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.WriteRecordContract

class WriteRecordModel {

    private var mOnListener: WriteRecordContract

    constructor(mOnListener:WriteRecordContract){
        this.mOnListener = mOnListener
    }

    fun saveRecordInServer(context: Context, contentValues: ContentValues, uriList: MutableList<String>?) {
        val url = context.getString(R.string.url) + "record/"
    }
}