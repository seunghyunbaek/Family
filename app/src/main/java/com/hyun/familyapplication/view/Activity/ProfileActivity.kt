package com.hyun.familyapplication.view.Activity

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.hyun.familyapplication.R
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        getAsyncTask(this@ProfileActivity).execute("http://192.168.200.125:8000/family/signin/", null, null)

        image_profile_back.setOnClickListener {
            // 뒤로가기
            onBackPressed()
        }

        text_profile_save.setOnClickListener {
            // 프로필 저장하기
            
        }
    }

    fun showLoading() {
        progress_profile.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progress_profile.visibility = View.INVISIBLE
    }

    companion object {
        class getAsyncTask internal constructor(context: ProfileActivity) :
            AsyncTask<String, String, String?>() {

            private var resp: String? = null
            private val activityReference: WeakReference<ProfileActivity> = WeakReference(context)
            val activity = activityReference.get()

            override fun doInBackground(vararg params: String?): String? {

                if(activity != null) activity.showLoading()

                val url = params[0]
                val obj = URL(url)
                with(obj.openConnection() as HttpURLConnection) {
                    // optional dfault is GET
                    requestMethod = "GET"
                    println("\nSending 'GET' request to URL : $url")
                    println("Response Code : $responseCode")
                    BufferedReader(InputStreamReader(inputStream)).use {
                        var response = it.readText()
//                        Log.d("HttpClientActivity", response)
                        println(response)
//                        val obj = JSONObject(response)
//                        println("json Object : " + obj.getString("email"))
                        return response
                    }
                }
                return null
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
//                val activity = activityReference.get()

                if(activity != null) {
                    activity.hideLoading()
                    val email = activity.findViewById<TextView>(R.id.text_profile_email)
                    val name = activity.findViewById<TextView>(R.id.text_profile_name)
                    val hoching = activity.findViewById<TextView>(R.id.text_profile_hoching)
//                    val gender = activity.findViewById<TextView>(R.id.text_profile_email)
                    val phone = activity.findViewById<TextView>(R.id.text_profile_phone)
                    val anniversary = activity.findViewById<TextView>(R.id.text_profile_anniversary)

                    val data = JSONObject(result)
                    email.setText(data.getString("email"))
                    name.setText(data.getString("name"))
//                    hoching.setText(data.getString("hoching"))
                    hoching.setText("나")
                    phone.setText(data.getString("phone"))
                    anniversary.setText(data.getString("anniversary"))
                }
//                Toast.makeText(activity, result, Toast.LENGTH_SHORT).show()
            }
        }

    }
}
