package com.hyun.myapplication.view.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.hyun.myapplication.R
import com.hyun.myapplication.contract.WriteRecordContract
import com.hyun.myapplication.DBHelper.FamilyDBOpenHelper
import com.hyun.myapplication.DBHelper.TestDBHelper
import com.hyun.myapplication.model.Record
import com.hyun.myapplication.presenter.WriteRecordPresenter
import kotlinx.android.synthetic.main.activity_write_record.*

class WriteRecordActivity : BaseActivity(), WriteRecordContract.View {

    private lateinit var wrPresenter: WriteRecordPresenter
    internal lateinit var db:TestDBHelper
    var id:Int = -1
    var record:Record ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_record)

        wrPresenter.takeView(this)
        db = TestDBHelper(this)

        setButton()

        val intent: Intent = intent
        if(intent.extras != null) {
            record = Record()
            val id: Int = intent.extras.getInt("id")
            val name: String = intent.extras.getString("name")
            val date: String = intent.extras.getString("date")
            val content: String = intent.extras.getString("content")

            record!!.id = id
            record!!.name = name
            record!!.date = date
            record!!.content = content

            etWR.text = Editable.Factory.getInstance().newEditable(content)
        }

    }

    fun setButton() {
        btnBackWR.setOnClickListener {
            onBackPressed()
        }

        btnWriteWR.setOnClickListener {
            if(record == null) {
                val record = Record()
                record.id = db.allRecord.last().id + 1
                record.name = "백승현"
                record.date = "2019년 8월 10일"
                record.content = etWR.text.toString()

                wrPresenter.saveRecord(this, db, record)
//            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            } else {
                record!!.content = etWR.text.toString()
                wrPresenter.updateRecord(this, db, record!!)
            }
            finish()
        }

        btnGallaryWR.setOnClickListener {
        }

        btnVideoWR.setOnClickListener {
            finish()
        }
    }

    override fun initPresenter() {
        wrPresenter = WriteRecordPresenter()
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun successRecord() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        finish()
    }

}
