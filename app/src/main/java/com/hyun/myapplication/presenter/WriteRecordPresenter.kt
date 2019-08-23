package com.hyun.myapplication.presenter

import android.content.Context
import com.hyun.myapplication.DBHelper.RecordDBHelper
import com.hyun.myapplication.contract.WriteRecordContract
import com.hyun.myapplication.model.Record

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
        recordDBHelper: RecordDBHelper,
        record: Record
    ) {
        // SQLite에 저장하기
//        val dbHandler = FamilyDBOpenHelper(context, null)
//        val record = Record(name, date, content)
//        dbHandler.addRecord(record)
//        wrView?.successRecord()

        recordDBHelper.addRecord(record)
    }

    override fun updateRecord(context: Context, recordDBHelper: RecordDBHelper, record: Record) {
        recordDBHelper.updateRecord(record)
    }
}