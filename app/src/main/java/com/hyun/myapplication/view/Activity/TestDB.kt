package com.hyun.myapplication.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hyun.myapplication.R
import com.hyun.myapplication.DBHelper.MindOrksDBOpenHelper
import com.hyun.myapplication.model.Record
import kotlinx.android.synthetic.main.activity_test_db.*

class TestDB : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_db)

        btnAddToDb.setOnClickListener {
            val dbHandler = MindOrksDBOpenHelper(this, null)
//            val user = Name(etName.text.toString())
            val record = Record(etName.text.toString(), "today", "content")
//            dbHandler.addName(name)
            dbHandler.addRecord(record)
            Toast.makeText(this, etName.text.toString() + "Added to database", Toast.LENGTH_LONG)
                .show()
        }
        btnShowDatafromDb.setOnClickListener {
            tvDisplayName.text = ""
            val dbHandler = MindOrksDBOpenHelper(this, null)
            val cursor = dbHandler.getAllName()
//            cursor!!.moveToFirst()
            if (cursor!!.moveToFirst()) {
                tvDisplayName.append((cursor.getString(cursor.getColumnIndex(MindOrksDBOpenHelper.COLUMN_NAME))))
                tvDisplayName.append((cursor.getString(cursor.getColumnIndex(MindOrksDBOpenHelper.COLUMN_ID))))
//                tvDisplayName.append((cursor.getString(cursor.getColumnIndex(MindOrksDBOpenHelper.COLUMN_DATE))))
//                tvDisplayName.append((cursor.getString(cursor.getColumnIndex(MindOrksDBOpenHelper.COLUMN_CONTENT))))
                tvDisplayName.append("\n")
            }
            while (cursor.moveToNext()) {
                tvDisplayName.append((cursor.getString(cursor.getColumnIndex(MindOrksDBOpenHelper.COLUMN_NAME))))
                tvDisplayName.append((cursor.getString(cursor.getColumnIndex(MindOrksDBOpenHelper.COLUMN_ID))))
//                tvDisplayName.append((cursor.getString(cursor.getColumnIndex(MindOrksDBOpenHelper.COLUMN_DATE))))
//                tvDisplayName.append((cursor.getString(cursor.getColumnIndex(MindOrksDBOpenHelper.COLUMN_CONTENT))))
                tvDisplayName.append("\n")
            }
            cursor.close()
        }

        btnRemoveDb.setOnClickListener {
            val dbHandler = MindOrksDBOpenHelper(this, null)
            dbHandler.removeAllName()
//            val cursor = dbHandler.removeAllName()

//            cursor!!.moveToFirst()
//            tvDisplayName.append((cursor.getString(cursor.getColumnIndex(MindOrksDBOpenHelper.COLUMN_NAME))))
//            while (cursor.moveToNext()) {
//                tvDisplayName.append((cursor.getString(cursor.getColumnIndex(MindOrksDBOpenHelper.COLUMN_NAME))))
//                tvDisplayName.append("\n")
//            }
//            cursor.close()

            Toast.makeText(this@TestDB, etName.text.toString() + "Delete All", Toast.LENGTH_LONG).show()
        }
    }
}
