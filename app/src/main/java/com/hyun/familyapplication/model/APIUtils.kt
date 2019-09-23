package com.hyun.familyapplication.model

import android.content.ContentValues
import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class APIUtils {
    companion object {
        // GET
        class getAsyncTask :
            AsyncTask<String, Any, String?>() {

            override fun doInBackground(vararg params: String?): String? {
                val urlString: String? = params[0]
                val url = URL(urlString)

                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "GET"

                    if (responseCode == 200) { // 성공 했을 때에만 데이터 읽어오기
                        BufferedReader(InputStreamReader(inputStream)).use {
                            val response = it.readText()
                            println("----------------------------------------------------------")
                            println("연결주소 : $urlString")
                            println("응답코드 : $responseCode")
                            println("응답메세지 : $responseMessage")
                            println("받은 데이터 : $response")
                            println("----------------------------------------------------------")
                            return response
                        }
                    }
                }
                return null
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                println("$result")
            }
        }

        // POST
        class postAsyncTask : AsyncTask<String, Any, String?>() {
            override fun doInBackground(vararg params: String?): String? {

                val urlString = params[0]
                val data = params[1]

                val url = URL(urlString)

                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "POST"

                    val wr = OutputStreamWriter(outputStream)
                    wr.write(data)
                    wr.flush()
                    wr.close()

                    println("---------------------------------------")
                    println("연결주소 : $urlString")
                    println("응답코드 : $responseCode")
                    println("응답메세지 : $responseMessage")
                    println("보낸 데이터 : $data")
                    println("---------------------------------------")
                }

                return null
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
            }
        }

        // PUT
        class putAsyncTask : AsyncTask<String, Any, String?>() {
            override fun doInBackground(vararg params: String?): String? {
                val urlString = params[0]
                val data = params[1]

                val url = URL(urlString)

                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "PUT"

                    val wr = OutputStreamWriter(outputStream)
                    wr.write(data)
                    wr.flush()
                    wr.close()

                    println("---------------------------------------")
                    println("연결주소 : $urlString")
                    println("응답코드 : $responseCode")
                    println("응답메세지 : $responseMessage")
                    println("보낸 데이터 : $data")
                    println("---------------------------------------")
                }

                return null
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
            }
        }

        // DELETE
        class delAsyncTask : AsyncTask<String, Any, String?>() {
            override fun doInBackground(vararg params: String?): String? {
                val urlString: String? = params[0]
                val url = URL(urlString)

                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "DELETE"
                    println("----------------------------------------------------------")
                    println("연결주소 : $urlString")
                    println("응답코드 : $responseCode")
                    println("응답메세지 : $responseMessage")
                    println("----------------------------------------------------------")
                }
                return null
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
            }
        }

        fun makeJson(contentValues: ContentValues): String {

            var isAnd = false
            var sbParams = ""

            for (parameter in contentValues.valueSet()) {
                var key = parameter.key
                var value = parameter.value.toString()

                // 파라미터가 두개 이상일때, 파라미터 사이에 &를 붙인다.
                if (isAnd)
                    sbParams += "&"

                sbParams =
                    sbParams + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(
                        value,
                        "UTF-8"
                    )

                // 파라미터가 2개 이상이면 isAnd를 true로 바꾸고 다음 루프부터 &를 붙인다.
                if (!isAnd)
                    if (contentValues.size() >= 2)
                        isAnd = true
            }

            return sbParams
        }
    }
}