package com.hyun.myapplication.contract

import android.content.Context
import com.hyun.myapplication.DBHelper.RecordDBHelper
import com.hyun.myapplication.model.Record
import com.hyun.myapplication.presenter.BasePresenter
import com.hyun.myapplication.view.BaseView

interface WriteRecordContract {
    interface View : BaseView {
        fun successRecord()
    }

    interface Presenter : BasePresenter<View> {
        fun saveRecord(context: Context, dbHelper: RecordDBHelper, record: Record)
        fun updateRecord(context: Context, dbHelper: RecordDBHelper, record: Record)
    }
}