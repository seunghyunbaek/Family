package com.hyun.familyapplication.contract

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.model.Record
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.BaseView

interface WriteRecordContract {
    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun successRecord()
    }

    interface Presenter : BasePresenter<View> {
        fun saveRecordInServer(context: Context, contentValues: ContentValues, uriList: MutableList<String>?)
        fun updateRecord(context: Context, dbHelper: DBHelper, record: Record)
        fun getPermission(context: Context)
    }

    interface onRecordListener {
        fun onSuccess(context: Context, str:String, uriList: MutableList<String>?)
        fun onFailure()
        fun onEnd(context:Context, result:String?)
    }
}