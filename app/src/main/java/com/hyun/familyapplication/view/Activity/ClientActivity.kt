package com.hyun.familyapplication.view.Activity

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hyun.familyapplication.R
import kotlinx.android.synthetic.main.activity_client.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class ClientActivity : AppCompatActivity() {

    private lateinit var activityReference: WeakReference<ClientActivity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        activityReference = WeakReference(this@ClientActivity)

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

            sendAsyncTask().execute("http://172.30.1.55:10381/elections/post/", email, name)
        }

        btn_client_get.setOnClickListener {
            getAsyncTask(this@ClientActivity).execute("http://172.30.1.55:10381/elections/getusers/", null, null)
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
                val email = params[1]
                val name = params[2]
//
                var reqParam = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                reqParam += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                val mURL = URL(url)

                with(mURL.openConnection() as HttpURLConnection) {
                    requestMethod = "POST"

                    val wr = OutputStreamWriter(outputStream)
                    wr.write(reqParam)
                    wr.flush()

                    println("URL : $url")
                    println("Response Code : $responseCode")
                    println("d $responseMessage")


//                    BufferedReader(InputStreamReader(inputStream)).use {
//                        val response = StringBuffer()
//
//                        var inputLine = it.readLine()
//                        while(inputLine != null) {
//                            response.append(inputLine)
//                            inputLine = it.readLine()
//                        }
//                        it.close()
//                        println("Response: $response")
//                    }
                }
                return null
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                println(result)
            }
        }
    }


//package com.hyun.myapplication.view.Activity
//
//import android.os.AsyncTask
//import android.os.Bundle
//import android.os.Handler
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.hyun.myapplication.R
//import java.io.BufferedReader
//import java.io.InputStreamReader
//import java.lang.ref.WeakReference
//import java.net.HttpURLConnection
//import java.net.URL
//
//class ClientActivity : AppCompatActivity() {
//
//    private lateinit var activityReference: WeakReference<ClientActivity>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_client)
//
//        activityReference = WeakReference(this@ClientActivity)
//
//        JSONTask().execute("http://172.30.1.4:10381")
//    }
//
//    class JSONTask : AsyncTask<String, String, String>() {
//        override fun doInBackground(vararg params: String?): String? {
//            val url = params[0]
//            val obj = URL(url)
//            with(obj.openConnection() as HttpURLConnection) {
//                // optional dfault is GET
//                requestMethod = "GET"
//                println("\nSending 'GET' request to URL : $url")
//                println("Response Code : $responseCode")
//                BufferedReader(InputStreamReader(inputStream)).use {
//                    var response = it.readText()
//                    Log.d("HttpClientActivity", response)
//                    return response
//                }
//            }
//            return null
//        }
//
//        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//            println("결과 =" + result)
//        }
//    }
//
//    fun showMessage(str:String) {
//        Toast.makeText(this@ClientActivity, str, Toast.LENGTH_SHORT).show()
//    }
}
