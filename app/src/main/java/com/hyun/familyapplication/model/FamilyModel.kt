package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.FamilyContract
import com.hyun.familyapplication.contract.TransferContract
import org.json.JSONArray
import org.json.JSONObject

class FamilyModel {
    companion object {
        fun getFamily(context: Context, listener: FamilyContract.Listener) {
            val dbHelper = DBHelper(context)
            val user = dbHelper.getUser()
            val url = context.getString(R.string.url) + "user/?room=" + user?.room

            APIUtils.getFamilyUserAsyncTask(listener, user?.email).execute(url)
        }

        fun getTransfer(context: Context, listener: TransferContract.Listener) {
            val dbHelper = DBHelper(context)
            val user = dbHelper.getUser()
            val url = context.getString(R.string.url) + "user/?room=" + user?.room

            APIUtils.getTransferUserAsyncTask(listener, user?.email).execute(url)
        }

        fun parseArray(result: String): ArrayList<User> {
            val jsonArray = JSONArray(result)
            val len = jsonArray.length()
            var cnt: Int = 0

            var list = ArrayList<User>()

            while (cnt < len) {

                val jsonObject = jsonArray.getJSONObject(cnt)
                val user = User()
                user.room = jsonObject.getInt("room")
                user.name = jsonObject.getString("name")
                user.email = jsonObject.getString("email")
                user.hoching = jsonObject.getString("hoching")
                user.anniversary = jsonObject.getString("anniversary")
                user.phone = jsonObject.getString("phone")
                user.gender = jsonObject.getString("gender")

                list.add(user)
                cnt++
            }

            return list
        }

        fun parseArray(result: String, email: String): ArrayList<User> {
            val jsonArray = JSONArray(result)
            val len = jsonArray.length()
            var cnt: Int = 0

            var list = ArrayList<User>()

            while (cnt < len) {

                val jsonObject = jsonArray.getJSONObject(cnt)
                val user = User()
                user.room = jsonObject.getInt("room")
                user.name = jsonObject.getString("name")
                user.email = jsonObject.getString("email")
                user.hoching = jsonObject.getString("hoching")
                user.anniversary = jsonObject.getString("anniversary")
                user.phone = jsonObject.getString("phone")
                user.gender = jsonObject.getString("gender")
                if (!email.equals(user.email))
                    list.add(user)
                cnt++
            }

            return list
        }

        fun deleteRoom(context: Context, listener: FamilyContract.Listener) {
            val db = DBHelper(context)
            val user = db.getUser()
            val url = context.getString(R.string.url) + "room/" + user?.room + "/"

            APIUtils.delRoomAsyncTask(context, listener).execute(url)
        }

        fun delSuccess(context: Context) {
            val db = DBHelper(context)
            val user = db.getUser()!!
            val cv = ContentValues()
            cv.put("email", user.email)
            cv.put("name", user.name)
            cv.put("hoching", user.hoching)
            cv.put("gender", user.gender)
            cv.put("phone", user.phone)
            cv.put("anniversary", user.anniversary)
            cv.put("room", 0)

            db.updateUserRoom(cv)
        }

        fun getHost(context: Context, listener: FamilyContract.Listener) {
            val db = DBHelper(context)
            val user = db.getUser()
            val url = context.getString(R.string.url) + "room/"+user?.room+"/"

            APIUtils.getHostAsyncTask(context, listener).execute(url)
        }

        fun mainOrTransfer(context: Context, result:String):Boolean {
            val db = DBHelper(context)
            val user = db.getUser()

            val jsonObject = JSONObject(result)

            if(user?.email.equals(jsonObject.getString("email")))
                return true
            else
                return false
        }

        fun updateUserRoom(context: Context, listener: FamilyContract.Listener, bool:Boolean) {
            val db = DBHelper(context)
            val user = db.getUser()

            val contentValues:ContentValues = ContentValues()
            contentValues.put("email", user?.email)
            contentValues.put("name", user?.name)
            contentValues.put("hoching", user?.hoching)
            contentValues.put("gender", user?.gender)
            contentValues.put("phone", user?.phone)
            contentValues.put("anniversary", user?.anniversary)
            contentValues.put("room", "")

            val json = APIUtils.makeJson(contentValues)
            val url = context.getString(R.string.url) + "user/"+user?.email+"/"

            APIUtils.putUser2AsyncTask(context, listener, bool).execute(url, json)
        }

        fun changeUserSQLite(context: Context, result:String):Int {
            val db = DBHelper(context)
            val user = db.getUser()!!
            val cv = ContentValues()
            cv.put("email", user.email)
            cv.put("name", user.name)
            cv.put("hoching", user.hoching)
            cv.put("gender", user.gender)
            cv.put("phone", user.phone)
            cv.put("anniversary", user.anniversary)
            cv.put("room", 0)

            val r = db.updateUserRoom(cv)
            return r
        }
    }
}