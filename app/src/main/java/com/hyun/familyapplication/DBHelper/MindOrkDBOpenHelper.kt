package com.hyun.familyapplication.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hyun.familyapplication.model.Name
import com.hyun.familyapplication.model.Record

class MindOrksDBOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context,
        DATABASE_NAME, factory,
        DATABASE_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase) {
        // create table name(_id integer primary key username text)
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME
                + " TEXT" + ")")
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }


    fun addName(name: Name) {
        val values = ContentValues()
        values.put(COLUMN_NAME, name.userName)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun addRecord(record: Record) {
        val values = ContentValues()
        values.put(COLUMN_NAME, record.name)
//        values.put(COLUMN_DATE, record.date)
//        values.put(COLUMN_CONTENT, record.content)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    fun getAllName(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

//    fun getAllRecord(): Cursor? {
//        val db = this.readableDatabase
//        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
//    }

    fun removeAllName() {
        val db = this.writableDatabase
//        db.rawQuery("DROP TABLE $TABLE_NAME", null)
//        db.rawQuery("DROP TABLE $TABLE_NAME", null)
        db.delete(TABLE_NAME,null,null)
//        db.delete(TABLE_NAME, "username=Name", null)
//        db.update(TABLE_NAME,"Name",)
//        val values = ContentValues()
//        val name = Name("kotlin")
//        values.put(COLUMN_ID, 1)
//        values.put(COLUMN_NAME, name.userName)
//        db.update(TABLE_NAME, values, "$COLUMN_ID=?", null)
    }

//    companion object {
//        private val DATABASE_VERSION = 1
//        private val DATABASE_NAME = "mindorksName.db"
//
//        val TABLE_NAME = "name"
//        val COLUMN_ID = "_id"
//        val COLUMN_NAME = "username"
//    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "familyRecord.db"
        val TABLE_NAME = "record"
        val COLUMN_ID = "_id"
        val COLUMN_NAME = "username"
        val COLUMN_DATE = "date"
        val COLUMN_CONTENT = "content"
    }
}