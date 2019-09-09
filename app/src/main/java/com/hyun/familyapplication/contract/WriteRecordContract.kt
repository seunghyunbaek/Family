package com.hyun.familyapplication.contract

import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.model.Record
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.BaseView

interface WriteRecordContract {
    interface View : BaseView {
        fun successRecord()
    }

    interface Presenter : BasePresenter<View> {
        fun saveRecord(context: Context, dbHelper: DBHelper, record: Record)
        fun updateRecord(context: Context, dbHelper: DBHelper, record: Record)
    }
}