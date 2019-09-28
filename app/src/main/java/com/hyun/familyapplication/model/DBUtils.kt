package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import android.os.Build
import com.hyun.familyapplication.DBHelper.DBHelper
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DBUtils {
    companion object {
        // Room 생성하기
        fun CreateRoom(context: Context, result:String): Int { // 유저가 새로운 기록 Room을 만든다
            val dbHelper = DBHelper(context)
            val user = dbHelper.getUser()
            val jsonObject = JSONObject(result)
            val email:String = jsonObject.getString("email")
            val id:Int = jsonObject.getInt("id")


            if (user?.room == 0) {
                val contentValues = ContentValues()
                contentValues.put("id", id)
                contentValues.put("email", email)
                dbHelper.addRoom(contentValues)

//                val contentValues2 = dbHelper.getRoom(user?.email)
//                user.room = contentValues2?.getAsInteger("room")

                val userValues = ContentValues()
                userValues.put(PROFILE_COOL_EMAIL, user.email)
                userValues.put(PROFILE_COOL_NAME, user.name)
                userValues.put(PROFILE_COOL_HOCHING, user.hoching)
                userValues.put(PROFILE_COOL_GENDER, user.gender)
                userValues.put(PROFILE_COOL_PHONE, user.phone)
                userValues.put(PROFILE_COOL_ANNIVERSARY, user.anniversary)
                userValues.put(PROFILE_COOL_ROOMID, id)

                val result = dbHelper.updateUserRoom(userValues)
                return result
            }
            return -1
        }

        // Room 체크하기
        fun checkRoom(context: Context): Boolean {
            val dbHelper = DBHelper(context)
            val user = dbHelper.getUser()

            if (user?.room == 0 || user == null)
                return false
            else
                return true
        }

        // Record 저장하기
        fun saveRecord(context: Context, contentValues: ContentValues): Long {
            val dbHelper = DBHelper(context)
            val user = dbHelper.getUser()
            val values = contentValues // content 들어있음

            // email
            values.put("$RECORD_COOL_EMAIL", user?.email)
            // name
            values.put("$RECORD_COOL_NAME", user?.name)
            // date
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var current = LocalDate.now()
                var formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
                var date = current.format(formatter)
                values.put("$RECORD_COOL_DATE", date)
            } else {
                var simpleDateFormatter = SimpleDateFormat("yyyyMMdd")
                var date = simpleDateFormatter.format(Date())
                values.put("$RECORD_COOL_DATE", date)
            }
            // room
            values.put("$RECORD_COOL_ROOMID", user?.room)
            val result = dbHelper.addRecord(values)

            return result
        }

        fun saveRecordImages(context: Context, recordid: Long, uriList: MutableList<String>?) {
            val recordid = recordid
            val dbHelper = DBHelper(context)
            if (uriList != null) {
                for (uri in uriList) {
                    val values = ContentValues()
                    values.put("$IMAGE_COOL_RECORDID", recordid)
                    values.put("$IMAGE_COOL_URL", uri)
                    dbHelper.addRecordImage(values)
                }
            }
        }

        fun getImageList(key:Int, lst:List<RecordImage>):List<String> {
            val rslt = ArrayList<String>()
            for(str in lst) {
                if(key == str.record)
                    rslt.add(str.uri)
            }
            return rslt
        }

        private val RECORD_TABLE_NAME = "Record"
        private val RECORD_COOL_ID = "id"
        private val RECORD_COOL_EMAIL = "email"
        private val RECORD_COOL_NAME = "name"
        private val RECORD_COOL_DATE = "date"
        private val RECORD_COOL_CONTENT = "content"
        private val RECORD_COOL_ROOMID = "room"

        private val TODO_TABLE_NAME = "Todo"
        private val TODO_COOL_ID = "id"
        private val TODO_COOL_TITLE = "title"
        private val TODO_COOL_DATE = "date"

        private val PROFILE_TABLE_NAME = "User"
        private val PROFILE_COOL_EMAIL = "email"
        private val PROFILE_COOL_NAME = "name"
        private val PROFILE_COOL_HOCHING = "hoching"
        private val PROFILE_COOL_GENDER = "gender"
        private val PROFILE_COOL_PHONE = "phone"
        private val PROFILE_COOL_ANNIVERSARY = "anniversary"
        private val PROFILE_COOL_ROOMID = "room"

        private val ROOM_TABLE_NAME = "Room"
        private val ROOM_COOL_ID = "id"
        private val ROOM_COOL_EMAIL = "email"

        private val IMAGE_TABLE_NAME = "RecordImage"
        private val IMAGE_COOL_ID = "id"
        private val IMAGE_COOL_RECORDID = "record"
        private val IMAGE_COOL_URL = "uri"
    }
}