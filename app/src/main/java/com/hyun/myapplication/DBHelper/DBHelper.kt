package com.hyun.myapplication.DBHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(
    context: Context,
    factory: SQLiteDatabase.CursorFactory?,
    dbName: String,
    dbVersion: Int
) :
    SQLiteOpenHelper(
        context,
        dbName, factory,
        dbVersion
    ) {
    override fun onCreate(db: SQLiteDatabase?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
