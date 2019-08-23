package com.hyun.myapplication.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hyun.myapplication.model.Record

class RecordDBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {

    companion object {
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "TESETKOTlIN.db"

        private val TABLE_NAME = "Person"
        private val COOL_ID = "Id"
        private val COOL_NAME = "Name"
        private val COOL_DATE = "Date"
        private val COOL_CONTENT = "Content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE $TABLE_NAME ($COOL_ID INTEGER PRIMARY KEY, $COOL_NAME TEXT, $COOL_DATE TEXT, $COOL_CONTENT TEXT)")

        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    val allRecord: List<Record>
        get() {
            val lstRecord = ArrayList<Record>()
            val selectQueryHandler = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQueryHandler, null)
            if (cursor.moveToFirst()) {
                do {
                    val record = Record()
                    record.id = cursor.getInt(cursor.getColumnIndex(COOL_ID))
                    record.name = cursor.getString(cursor.getColumnIndex(COOL_NAME))
                    record.date = cursor.getString(cursor.getColumnIndex(COOL_DATE))
                    record.content = cursor.getString(cursor.getColumnIndex(COOL_CONTENT))

                    lstRecord.add(record)
                } while (cursor.moveToNext())
            }
            db.close()
            return lstRecord
        }

    fun addRecord(record: Record) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COOL_ID, record.id)
        values.put(COOL_NAME, record.name)
        values.put(COOL_DATE, record.date)
        values.put(COOL_CONTENT, record.content)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateRecord(record: Record): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COOL_ID, record.id)
        values.put(COOL_NAME, record.name)
        values.put(COOL_DATE, record.date)
        values.put(COOL_CONTENT, record.content)

        return db.update(TABLE_NAME, values, "$COOL_ID=?", arrayOf(record.id.toString()))
    }

    fun deleteRecord(record:Record) {
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "$COOL_ID=?", arrayOf(record.id.toString()))
        db.close()
    }

}