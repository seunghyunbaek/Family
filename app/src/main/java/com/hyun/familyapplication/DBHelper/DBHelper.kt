package com.hyun.familyapplication.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.hyun.familyapplication.model.Record
import com.hyun.familyapplication.model.RecordImage
import com.hyun.familyapplication.model.Todo
import com.hyun.familyapplication.model.User

class DBHelper(
    context: Context
) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME, null,
        DATABASE_VER
    ) {

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("jkljkljkl", "디비 onCreate() 실행")
        val CREATE_TABLE_ROOM =
            ("CREATE TABLE $ROOM_TABLE_NAME (" +
                    "$ROOM_COOL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$ROOM_COOL_EMAIL TEXT" +
                    ")")
        val CREATE_TABLE_RECORD =
            ("CREATE TABLE $RECORD_TABLE_NAME (" +
                    "$RECORD_COOL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$RECORD_COOL_EMAIL TEXT, " +
                    "$RECORD_COOL_NAME TEXT, " +
                    "$RECORD_COOL_DATE TEXT, " +
                    "$RECORD_COOL_CONTENT TEXT, " +
                    "$RECORD_COOL_ROOMID INTEGER, " +
                    "FOREIGN KEY($RECORD_COOL_ROOMID) REFERENCES $ROOM_TABLE_NAME($ROOM_COOL_ID) ON DELETE CASCADE ON UPDATE NO ACTION" +
                    ")")
        val CREATE_TABLE_TODO =
            ("CREATE TABLE $TODO_TABLE_NAME (" +
                    "$TODO_COOL_ID INTEGER PRIMARY KEY, " +
                    "$TODO_COOL_TITLE TEXT, " +
                    "$TODO_COOL_DATE TEXT" +
                    ")")
        val CREATE_TABLE_USER =
            ("CREATE TABLE $PROFILE_TABLE_NAME (" +
                    "$PROFILE_COOL_EMAIL TEXT PRIMARY KEY, " +
                    "$PROFILE_COOL_NAME TEXT, " +
                    "$PROFILE_COOL_HOCHING TEXT, " +
                    "$PROFILE_COOL_GENDER TEXT, " +
                    "$PROFILE_COOL_PHONE TEXT, " +
                    "$PROFILE_COOL_ANNIVERSARY TEXT," +
                    "$PROFILE_COOL_ROOMID INTEGER," +
                    "FOREIGN KEY($PROFILE_COOL_ROOMID) REFERENCES $ROOM_TABLE_NAME($ROOM_COOL_ID) ON UPDATE NO ACTION" +
                    ")")
        val CREATE_TABLE_IMAGETABLE =
            ("CREATE TABLE $IMAGE_TABLE_NAME (" +
                    "$IMAGE_COOL_ID INTEGER PRIMARY KEY, " +
                    "$IMAGE_COOL_RECORDID INTEGER, " +
                    "$IMAGE_COOL_URL TEXT, " +
                    "FOREIGN KEY($IMAGE_COOL_RECORDID) REFERENCES $RECORD_TABLE_NAME($RECORD_COOL_ID) ON DELETE CASCADE ON UPDATE NO ACTION" +
                    ")")

        db!!.execSQL(CREATE_TABLE_ROOM)
        db!!.execSQL(CREATE_TABLE_RECORD)
        db!!.execSQL(CREATE_TABLE_TODO)
        db!!.execSQL(CREATE_TABLE_USER)
        db!!.execSQL(CREATE_TABLE_IMAGETABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $ROOM_TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $RECORD_TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $TODO_TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $PROFILE_TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $IMAGE_TABLE_NAME")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        super.onDowngrade(db, oldVersion, newVersion)

        Log.d("jkljkljkl", "디비 onDowngrade() 실행")
        db!!.execSQL("DROP TABLE IF EXISTS $ROOM_TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $RECORD_TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $TODO_TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $PROFILE_TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $IMAGE_TABLE_NAME")
        onCreate(db)
    }

    // [START Room TABLE]
    fun addRoom(contentValues: ContentValues) {
        val values = contentValues
        val db = this.writableDatabase
        db.insert(ROOM_TABLE_NAME, null, values)
        db.close()
    }

    fun getRoom(email: String): ContentValues? {
        val db = writableDatabase
        val selectQueryHandler = "SELECT * FROM $ROOM_TABLE_NAME WHERE email='$email'"
        val cursor = db.rawQuery(selectQueryHandler, null)
        val contentValues = ContentValues()
        if (cursor.moveToFirst()) {
            do {
//                contentValues.put("email", cursor.getString(cursor.getColumnIndex(ROOM_COOL_EMAIL)))
                contentValues.put("roomid", cursor.getInt(cursor.getColumnIndex(ROOM_COOL_ID)))
            } while (cursor.moveToNext())
        } else {
            return null
        }

        return contentValues
    }

    fun deleteRoom() {
        val db = this.writableDatabase
        db.delete(ROOM_TABLE_NAME, null, null)
        db.delete(PROFILE_TABLE_NAME, null, null)
        db.delete(RECORD_TABLE_NAME, null, null)
    }

    // [END Room TABLE]

    // [START Record TABLE]
    val allRecord: List<Record>
        get() {
            val lstRecord = ArrayList<Record>()
            val selectQueryHandler = "SELECT * FROM $RECORD_TABLE_NAME"
//            val selectQueryHandler = "SELECT * FROM $PROFILE_TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQueryHandler, null)
            if (cursor.moveToFirst()) {
                do {
                    val record = Record()
                    record.id = cursor.getInt(cursor.getColumnIndex(RECORD_COOL_ID))
                    record.email = cursor.getString(cursor.getColumnIndex(RECORD_COOL_EMAIL))
                    record.name = cursor.getString(cursor.getColumnIndex(RECORD_COOL_NAME))
                    record.date = cursor.getString(cursor.getColumnIndex(RECORD_COOL_DATE))
                    record.content = cursor.getString(cursor.getColumnIndex(RECORD_COOL_CONTENT))
                    record.roomid = cursor.getInt(cursor.getColumnIndex(RECORD_COOL_ROOMID))

                    lstRecord.add(record)
                } while (cursor.moveToNext())
            }
            db.close()
            return lstRecord
        }

//    fun addRecord(record: Record) {
//        val db = this.writableDatabase
//        val values = ContentValues()
//        values.put(RECORD_COOL_ID, record.id)
//        values.put(RECORD_COOL_NAME, record.name)
//        values.put(RECORD_COOL_DATE, record.date)
//        values.put(RECORD_COOL_CONTENT, record.content)

    //        db.insert(RECORD_TABLE_NAME, null, values)
//        db.close()
//    }
    fun addRecord(contentValues: ContentValues):Long {
        val db = this.writableDatabase
        val values = contentValues
        val result = db.insert(RECORD_TABLE_NAME, null, values)
        println("----------------기록 저장하기 결과------------------------")
        println(result)
        println("---------------------------------------------------------")

        db.close()
        return result
    }

    fun updateRecord(record: Record): Int {
        val db = this.writableDatabase
        val values = ContentValues()
//        values.put(RECORD_COOL_ID, record.id)
        values.put(RECORD_COOL_NAME, record.name)
        values.put(RECORD_COOL_DATE, record.date)
        values.put(RECORD_COOL_CONTENT, record.content)

//        return db.update(
//            RECORD_TABLE_NAME,
//            values,
//            "$RECORD_COOL_ID=?",
//            arrayOf(record.id.toString())
//        )
        return -1
    }

    fun deleteRecord(record: Record) {
        val db = this.writableDatabase

//        db.delete(RECORD_TABLE_NAME, "$RECORD_COOL_ID=?", arrayOf(record.id.toString()))
        db.close()
    }
    // [END Record TABLE]

    // [START Todo TABLE]
    val allTodo: List<Todo>
        get() {
            val lstTodo = ArrayList<Todo>()
            val selectQueryHandler = "SELECT * FROM $TODO_TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQueryHandler, null)
            if (cursor.moveToFirst()) {
                do {
                    val todo = Todo()
                    todo.id = cursor.getInt(cursor.getColumnIndex(TODO_COOL_ID))
                    todo.title = cursor.getString(cursor.getColumnIndex(TODO_COOL_TITLE))
                    todo.date = cursor.getString(cursor.getColumnIndex(TODO_COOL_DATE))

                    lstTodo.add(todo)
                } while (cursor.moveToNext())
            }

            db.close()
            return lstTodo
        }

    fun addTodo(todo: Todo) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TODO_COOL_ID, todo.id)
        values.put(TODO_COOL_TITLE, todo.title)
        values.put(TODO_COOL_DATE, todo.date)

        db.insert(TODO_TABLE_NAME, null, values)
        db.close()
    }

    fun updateTodo(todo: Todo): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TODO_COOL_ID, todo.id)
        values.put(TODO_COOL_TITLE, todo.title)
        values.put(TODO_COOL_DATE, todo.date)

        return db.update(TODO_TABLE_NAME, values, "$TODO_COOL_ID=?", arrayOf(todo.id.toString()))
    }

    fun deleteTodo(todo: Todo) {
        val db = this.writableDatabase

        db.delete(TODO_TABLE_NAME, "$TODO_COOL_ID=?", arrayOf(todo.id.toString()))
        db.close()
    }
    // [END Todo TABLE]

    // [START User TABLE]
    fun getUser(): User? {
        val selectQueryHandler =
            "SELECT * FROM $PROFILE_TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQueryHandler, null)
        val user: User = User();

        if (cursor.moveToFirst()) {
            do {
                user.email = cursor.getString(cursor.getColumnIndex(PROFILE_COOL_EMAIL))
                user.name = cursor.getString(cursor.getColumnIndex(PROFILE_COOL_NAME))
                user.hoching = cursor.getString(cursor.getColumnIndex(PROFILE_COOL_HOCHING))
                user.gender = cursor.getString(cursor.getColumnIndex(PROFILE_COOL_GENDER))
                user.phone = cursor.getString(cursor.getColumnIndex(PROFILE_COOL_PHONE))
                user.anniversary = cursor.getString(cursor.getColumnIndex(PROFILE_COOL_ANNIVERSARY))
                user.roomId = cursor.getInt(cursor.getColumnIndex(PROFILE_COOL_ROOMID))
            } while (cursor.moveToNext())
        } else {
            return null
        }
        db.close()

        return user
    }

    fun addUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(PROFILE_COOL_EMAIL, user.email)
        values.put(PROFILE_COOL_NAME, user.name)
        values.put(PROFILE_COOL_HOCHING, user.hoching)
        values.put(PROFILE_COOL_GENDER, user.gender)
        values.put(PROFILE_COOL_PHONE, user.phone)
        values.put(PROFILE_COOL_ANNIVERSARY, user.anniversary)
        values.put(PROFILE_COOL_ROOMID, user.roomId)
        db.insert(PROFILE_TABLE_NAME, null, values)
        db.close()
    }

    fun updateUserRoom(contentValue: ContentValues): Int {
        val db = this.writableDatabase
        val values = contentValue
        val email = values.getAsString(PROFILE_COOL_EMAIL)
        val result = db.update(PROFILE_TABLE_NAME, values, "$PROFILE_COOL_EMAIL=?", arrayOf(email))
        return result
    }

    fun deleteUser() {
        val db = this.writableDatabase
        db.delete(PROFILE_TABLE_NAME, null, null)
    }
    // [END User TABLE]

    // [Start RecordImage TABLE]
    val allImages: List<RecordImage>
        get() {
            val lstImages = ArrayList<RecordImage>()
            val selectQueryHandler = "SELECT * FROM $IMAGE_TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQueryHandler, null)
            if (cursor.moveToFirst()) {
                do {
                    val recordImage = RecordImage()
                    recordImage.id = cursor.getInt(cursor.getColumnIndex(IMAGE_COOL_ID))
                    recordImage.recordid = cursor.getInt(cursor.getColumnIndex(IMAGE_COOL_RECORDID))
                    recordImage.uri = cursor.getString(cursor.getColumnIndex(IMAGE_COOL_URL))

                    lstImages.add(recordImage)
                } while (cursor.moveToNext())
            }

            db.close()
            return lstImages
        }

    fun addRecordImage(contentValues: ContentValues) {
        val db = this.writableDatabase
        val values = contentValues
        val result = db.insert(IMAGE_TABLE_NAME, null, values)
        println("--------------------------------------------------")
        println(result)
        println("--------------------------------------------------")
        db.close()
    }
    // [END RecordImage TABLE]


    companion object {
        private val DATABASE_NAME = "KFAMILY.db"
        private val DATABASE_VER = 4

        private val RECORD_TABLE_NAME = "Record"
        private val RECORD_COOL_ID = "Id"
        private val RECORD_COOL_EMAIL = "Email"
        private val RECORD_COOL_NAME = "Name"
        private val RECORD_COOL_DATE = "Date"
        private val RECORD_COOL_CONTENT = "Content"
        private val RECORD_COOL_ROOMID = "RoomId"

        private val TODO_TABLE_NAME = "Todo"
        private val TODO_COOL_ID = "Id"
        private val TODO_COOL_TITLE = "Title"
        private val TODO_COOL_DATE = "Date"

        private val PROFILE_TABLE_NAME = "User"
        private val PROFILE_COOL_EMAIL = "Email"
        private val PROFILE_COOL_NAME = "Name"
        private val PROFILE_COOL_HOCHING = "Hoching"
        private val PROFILE_COOL_GENDER = "Gender"
        private val PROFILE_COOL_PHONE = "Phone"
        private val PROFILE_COOL_ANNIVERSARY = "Anniversary"
        private val PROFILE_COOL_ROOMID = "RoomId"

        private val ROOM_TABLE_NAME = "Room"
        private val ROOM_COOL_ID = "Id"
        private val ROOM_COOL_EMAIL = "Email"

        private val IMAGE_TABLE_NAME = "RecordImage"
        private val IMAGE_COOL_ID = "ID"
        private val IMAGE_COOL_RECORDID = "RecordId"
        private val IMAGE_COOL_URL = "Url"
    }

}
