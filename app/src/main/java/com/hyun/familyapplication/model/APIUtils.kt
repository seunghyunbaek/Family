package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import android.os.AsyncTask
import com.hyun.familyapplication.contract.MyHomeCheckContract
import com.hyun.familyapplication.contract.SignInContract
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder


class APIUtils {

    companion object {
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

    // GET
    class getAsyncTask() :
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

    class postRoomAsyncTask(
        mOnListener: MyHomeCheckContract.onMyHomeCheckListener,
        context: Context
    ) : AsyncTask<String, Any, String?>() {

        val mOnListener: MyHomeCheckContract.onMyHomeCheckListener
        val context: Context

        init {
            this.mOnListener = mOnListener
            this.context = context
        }

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
                println("응답코드 : $responseCode") // 200 201 등등
                println("응답메세지 : $responseMessage") // Created
                println("보낸 데이터 : $data")
                println("---------------------------------------")

                if (responseCode == 201) { // 성공 했을 때에만 데이터 읽어오기
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
            if (result != null) {
                mOnListener.onSuccess(context, result)
                println("-------------------------------------------------------------------------")
                println("-----------------       post onPostExecute(성공)      -------------------")
                println("-------------------------------------------------------------------------")
            } else {
                println("-------------------------------------------------------------------------")
                println("-----------------       post onPostExecute(실패)      -------------------")
                println("-------------------------------------------------------------------------")
                mOnListener.onFailure()
            }
        }
    }

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
                println("응답코드 : $responseCode") // 200 201 등등
                println("응답메세지 : $responseMessage") // Created
                println("보낸 데이터 : $data")
                println("---------------------------------------")
                return responseCode.toString()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result?.toInt() == 201) {
                println("-------------------------------------------------------------------------")
                println("-----------------       post onPostExecute(성공)      -------------------")
                println("-------------------------------------------------------------------------")
            } else {
                println("-------------------------------------------------------------------------")
                println("-----------------       post onPostExecute(실패)      -------------------")
                println("-------------------------------------------------------------------------")
            }
        }
    }

    // POST SignIn
    class postSignInAsyncTask(
        mOnListener: SignInContract.onSignInListener,
        context: Context,
        email: String,
        name: String
    ) : AsyncTask<String, Any, String?>() {

        val mOnListener: SignInContract.onSignInListener
        val context: Context
        val email: String
        val name: String

        init {
            this.mOnListener = mOnListener
            this.context = context
            this.email = email
            this.name = name
        }

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
                println("응답코드 : $responseCode") // 200 201 등등
                println("응답메세지 : $responseMessage") // Created
                println("보낸 데이터 : $data")
                println("---------------------------------------")
                return responseCode.toString()
            }

            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result?.toInt() == 201) {
                mOnListener.onEnd(context, email, name)
                println("-------------------------------------------------------------------------")
                println("-----------------       post onPostExecute(성공)      -------------------")
                println("-------------------------------------------------------------------------")
            } else {
                println("-------------------------------------------------------------------------")
                println("-----------------       post onPostExecute(실패)      -------------------")
                println("-------------------------------------------------------------------------")
                mOnListener.onFailure()
            }
        }
    }

    // PUT
    class putRoomAsyncTask(
        mOnListener: MyHomeCheckContract.onMyHomeCheckListener,
        context: Context
    ) : AsyncTask<String, Any, String?>() {

        val mOnListener: MyHomeCheckContract.onMyHomeCheckListener
        val context: Context

        init {
            this.mOnListener = mOnListener
            this.context = context
        }

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
            if(result != null) {
                mOnListener.onEnd(context, result)
            }
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


}