package com.hyun.familyapplication.view.Activity

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.model.User
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class ProfileActivity : AppCompatActivity() {

    lateinit var stringUrl :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        stringUrl = getString(R.string.url)

        val dbHelper = DBHelper(this)
        val user = dbHelper.getUser()

        getAsyncTask(this@ProfileActivity).execute(stringUrl + "user/" + user?.email, null, null)

        image_profile_back.setOnClickListener {
            // 뒤로가기
            onBackPressed()
        }

        text_profile_save.setOnClickListener {
            // 프로필 저장하기
            if(toggle_profile_gender.isChecked) {
                sendAsyncTask().execute(stringUrl + "family/signin/1",
                    text_profile_email.text.toString(),
                    text_profile_name.text.toString(),
                    text_profile_hoching.text.toString(),
                    toggle_profile_gender.textOn.toString(),
                    text_profile_phone.text.toString(),
                    text_profile_anniversary.text.toString())
            } else {
                sendAsyncTask().execute(stringUrl + "family/signin/1",
                    text_profile_email.text.toString(),
                    text_profile_name.text.toString(),
                    text_profile_hoching.text.toString(),
                    toggle_profile_gender.textOff.toString(),
                    text_profile_phone.text.toString(),
                    text_profile_anniversary.text.toString())
            }
            Toast.makeText(this@ProfileActivity, "수정되었습니다.", Toast.LENGTH_SHORT).show();
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
                try {
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
                } catch (e:Exception) {
                    println("------getAsyncTask Error : ProfileActivity")
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
                    email.setText(data.getString("email").replace("_", "."))
                    name.setText(data.getString("name"))
//                    hoching.setText(data.getString("hoching"))
                    hoching.setText("나")
                    phone.setText(data.getString("phone"))
                    anniversary.setText(data.getString("anniversary"))
                }
//                Toast.makeText(activity, result, Toast.LENGTH_SHORT).show()
            }
        }


        class sendAsyncTask : AsyncTask<String, Any, String?>() {
            override fun doInBackground(vararg params: String?): String? {

                val url = params[0]
                val email = params[1]
                val name = params[2]
                val hoching = params[3]
                val gender = params[4]
                val phone = params[5]
                val anniversary = params[6]

                var reqParam = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                reqParam += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                reqParam += "&" + URLEncoder.encode("hoching", "UTF-8") + "=" + URLEncoder.encode(hoching, "UTF-8")
                reqParam += "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8")
                reqParam += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")
                reqParam += "&" + URLEncoder.encode("anniversary", "UTF-8") + "=" + URLEncoder.encode(anniversary, "UTF-8")

                val mURL = URL(url)

                try {
                    with(mURL.openConnection() as HttpURLConnection) {
                        requestMethod = "POST"

                        val wr = OutputStreamWriter(outputStream)
                        wr.write(reqParam)
                        wr.flush()

                        println("URL : $url")
                        println("Response Code : $responseCode")
                        println("d $responseMessage")
                    }
                } catch (e:Exception) {
                    println("------sendAsyncTask Error : ProfileActivity")
                }
                return null
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                println(result)
            }
        }

    }
}
