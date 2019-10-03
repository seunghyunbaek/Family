package com.hyun.familyapplication.model

import android.content.Context
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.PictureContract
import org.json.JSONArray
import org.json.JSONObject

class PictureModel {
    companion object {
        fun getPicture(context: Context, listener:PictureContract.Listener) {
            val url = context.getString(R.string.url) + "image/"
            APIUtils.getPictureAsyncTask(listener).execute(url)
        }

        fun parseArray(result:String):ArrayList<RecordImage> {
            var jsonArray = JSONArray(result)
            val len:Int = jsonArray.length()
            var cnt:Int = 0

            var list = ArrayList<RecordImage>()

            while(cnt < len) {
                val jsonObject:JSONObject = jsonArray.getJSONObject(cnt)
                val picture = RecordImage()
                picture.id = jsonObject.getInt("id")
                picture.record = jsonObject.getInt("record")
                picture.uri = jsonObject.getString("uri")

                list.add(picture)
                cnt++
            }

            return list
        }
    }
}