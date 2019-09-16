package com.hyun.familyapplication.view.Activity

import android.content.ContentValues
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hyun.familyapplication.R
import kotlinx.android.synthetic.main.activity_client.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class ClientActivity : AppCompatActivity() {

    private lateinit var activityReference: WeakReference<ClientActivity>
    private lateinit var url:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        activityReference = WeakReference(this@ClientActivity)
        url = getString(R.string.url)

        val mAuth = FirebaseAuth.getInstance()
        val mFirebaseUser = mAuth.currentUser
        if(mFirebaseUser != null) {
            val email = mFirebaseUser.email
            val name = mFirebaseUser.displayName

            edit_client_id.setText(email)
            edit_client_pw.setText(name)
        }

        btn_client_send.setOnClickListener {
            val email:String = edit_client_id.text.toString()
            val name = edit_client_pw.text.toString()

            val sendurl = url + "users/testuser@gmail_com/"

            var json:JSONObject = JSONObject()
//            json.put("email", email)
//            json.put("name", name)
            json.put("email", "test@test_com")
            json.put("name", "teste")

            var cv:MutableMap<String, String> = HashMap<String,String>()
            cv.put("email", "testuser@gmail_com")
            cv.put("name", "testtest")

            var sbParams:StringBuffer = StringBuffer()
            var sbParams2:String = ""
            var key:String
            var value:String

            var reqParam4 = ""

            var isAnd:Boolean = false

            for (parameter in cv) {
                key = parameter.key
                value = parameter.value

                if(isAnd) {
                    sbParams.append("&")
                    sbParams2 += "&"
                    reqParam4 += "&"
                }

                sbParams.append(key).append("=").append(value)
                sbParams2 = sbParams2 + key + "=" + value
                reqParam4 = reqParam4 + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8")

                if(!isAnd)
                    if(cv.size >= 2)
                        isAnd = true
            }

            var reqParam = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode("testuser@gmail_com", "UTF-8")
            reqParam += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode("tester", "UTF-8")

            var reqParam2 = URLEncoder.encode(sbParams.toString(), "UTF-8")
            var reqParam3 = URLEncoder.encode(sbParams2.toString(), "UTF-8")


            sendAsyncTask().execute(sendurl, reqParam4)
        }

        btn_client_get.setOnClickListener {
            var json:JSONObject = JSONObject()
            json.put("email", "test@gmail_com")
            json.put("name", "testuser")
            println("json test:" + json.toString())

//            val geturl = url + "users/qwe@gmail_com"
            val geturl = url + "users/"
            getAsyncTask(this@ClientActivity).execute(geturl, null, null)
        }
        btn_google_data.setOnClickListener {
            val mAuth = FirebaseAuth.getInstance()
            val mFirebaseUser = mAuth.currentUser
            if(mFirebaseUser != null) {
                val email = mFirebaseUser.email
                val name = mFirebaseUser.displayName

                edit_client_id.setText(email)
                edit_client_pw.setText(name)
            }
        }
    }

    companion object {
        class getAsyncTask internal constructor(context: ClientActivity) :
            AsyncTask<String, String, String?>() {

            private var resp: String? = null
            private val activityReference: WeakReference<ClientActivity> = WeakReference(context)

            override fun doInBackground(vararg params: String?): String? {
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
                        return response
                    }
                }
                return null
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                val activity = activityReference.get()
                Toast.makeText(activity, result, Toast.LENGTH_SHORT).show()
            }
        }

        //새로운 TASK정의 (AsyncTask)
        // < >안에 들은 자료형은 순서대로 doInBackground, onProgressUpdate, onPostExecute의 매개변수 자료형을 뜻한다.
        class sendAsyncTask : AsyncTask<String, Any, String?>() {
            override fun doInBackground(vararg params: String?): String? {

                val url = params[0]
//                val email = params[1]
//                val name = params[2]
                val jsonString = params[1]

//                println(jsonString)
//
                var reqParam = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode("testuser@gmail_com", "UTF-8")
                reqParam += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode("tester", "UTF-8")

                val mURL = URL(url)
//                var reqParam = URLEncoder.encode(jsonString, "UTF-8")

                with(mURL.openConnection() as HttpURLConnection) {
//                    requestMethod = "POST"
//                    requestMethod = "PUT"
                    requestMethod = "DELETE"
//                    setRequestProperty("Accept-Charset", "UTF-8")
//                    setRequestProperty("Context_Type", "application/json")

//                    val wr = OutputStreamWriter(outputStream)
//                    wr.write(reqParam)
//                    wr.write(jsonString)
//                    wr.flush()

                    println("URL : $url")
                    println("Response Code : $responseCode")
                    println("d $responseMessage")

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
