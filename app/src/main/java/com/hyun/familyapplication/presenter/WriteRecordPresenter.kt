package com.hyun.familyapplication.presenter

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.contract.WriteRecordContract
import com.hyun.familyapplication.model.Record
import com.hyun.familyapplication.model.WriteRecordModel

class WriteRecordPresenter : WriteRecordContract.Presenter, WriteRecordContract.onRecordListener {

    private var wrView: WriteRecordContract.View? = null
    private var writeRecordModel: WriteRecordModel

    constructor() {
        writeRecordModel = WriteRecordModel(this)
    }

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

    override fun saveRecordInServer(
        context: Context,
        contentValues: ContentValues,
        uriList: MutableList<String>?
    ) {
        // SQLite에 저장하기
//        val dbHandler = FamilyDBOpenHelper(context, null)
//        val record = Record(name, date, content)
//        dbHandler.addRecord(record)
//        wrView?.successRecord()
//        dbHelper.addRecord(record)

        writeRecordModel.saveRecordInServer(context, contentValues, uriList)

        /////////////////////////SQLite///////////////////
//        val result = DBUtils.saveRecord(context, contentValues)
//        DBUtils.saveRecordImages(context, result, uriList)
//        wrView?.successRecord()
    }

    override fun updateRecord(context: Context, dbHelper: DBHelper, record: Record) {
        dbHelper.updateRecord(record)
    }

    override fun getPermission(context: Context) {
    }


    override fun onSuccess(context: Context, recordResult_: String, uriList: MutableList<String>?) {
        if (uriList?.size == 0) {
            onEnd(context, recordResult_)
//            wrView?.successRecord()
        } else {
            // str : {id:01, name:hyun .... }
            val recordResult: String = recordResult_
            writeRecordModel.saveImageInServer(context, recordResult, uriList)
        }
    }

    override fun onFailure() {
    }

    override fun onEnd(context: Context, result: String?) {
        wrView?.successRecord()
    }
}