package com.hyun.familyapplication.presenter

import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.contract.WriteRecordContract
import com.hyun.familyapplication.model.Record

class WriteRecordPresenter : WriteRecordContract.Presenter {

    private var wrView: WriteRecordContract.View? = null

    //  View와 Presenter를 연결
    override fun takeView(view: WriteRecordContract.View) {
        wrView = view
    }

    // Viewrk 제거된 것을 프레젠터에 알려준다
    override fun dropView() {
        wrView = null
    }

    // 작성한 기록을 저장한다
//    override fun saveRecord(context: Context, testDBHelper: RecordDBHelper, name: String, date: String, content: String) {
//        // SQLite에 저장하기
//        val dbHandler = FamilyDBOpenHelper(context, null)
//        val record = Record(name, date, content)
//        dbHandler.addRecord(record)
//        wrView?.successRecord()
//    }

    override fun saveRecord(
        context: Context,
        dbHelper: DBHelper,
        record: Record
    ) {
        // SQLite에 저장하기
//        val dbHandler = FamilyDBOpenHelper(context, null)
//        val record = Record(name, date, content)
//        dbHandler.addRecord(record)
//        wrView?.successRecord()

        dbHelper.addRecord(record)
    }

    override fun updateRecord(context: Context, dbHelper: DBHelper, record: Record) {
        dbHelper.updateRecord(record)
    }
}