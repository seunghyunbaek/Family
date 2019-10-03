package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import android.os.AsyncTask
import com.hyun.familyapplication.contract.*
import cz.msebera.android.httpclient.client.methods.HttpPost
import cz.msebera.android.httpclient.entity.ContentType
import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder
import cz.msebera.android.httpclient.entity.mime.content.FileBody
import cz.msebera.android.httpclient.entity.mime.content.StringBody
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
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
            if (result != null) {
                println("$result")
            }
        }
    }

    // GET
    class getFamilyUserAsyncTask(listener: FamilyContract.Listener) :
        AsyncTask<String, Any, String?>() {

        private val listener : FamilyContract.Listener

        init {
            this.listener = listener
        }

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
            if (result != null) {
                println("$result")
                listener.onSuccess(result)
            } else {
                println("--------------------------------------------")
                println("룸 유저 : 통신 실패")
                println("--------------------------------------------")
            }
        }
    }

    // GET Image
    class getPictureAsyncTask(mListener: PictureContract.Listener) :
        AsyncTask<String, Any, String?>() {

        private val mListener: PictureContract.Listener

        init {
            this.mListener = mListener
        }

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
                } else {
                    println("--------------------------------------------------------------")
                    println("실패 코드 : $responseCode")
                    println("실패 메세지 : $responseMessage")
                    println("--------------------------------------------------------------")
                }
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {
                println("$result")
                mListener.onSeccess(result)
            } else {
                println("Picture 통신 실패")
            }
        }
    }

    // GET Image
    class getImageAsyncTask(mOnListener: RecordContract.onRecordListener) :
        AsyncTask<String, Any, String?>() {

        val mOnListener: RecordContract.onRecordListener

        init {
            this.mOnListener = mOnListener
        }

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
            if (result != null) {
                println("$result")
                mOnListener.onEnd(result)
            }
        }
    }

    // GET Record
    class getRecordAsyncTask(mOnListener: RecordContract.onRecordListener) :
        AsyncTask<String, Any, String?>() {

        val mOnListener: RecordContract.onRecordListener

        init {
            this.mOnListener = mOnListener
        }

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
            if (result != null) {
                println("$result")
                mOnListener.onSuccess(result)
            }
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

    // POST Image
    class postImageAsyncTask(
        mOnListener: WriteRecordContract.onRecordListener,
        context: Context,
        uriList: MutableList<String>?
    ) : AsyncTask<String, Any, String?>() {

        val mOnListener: WriteRecordContract.onRecordListener
        val context: Context
        val uriList: MutableList<String>?

        init {
            this.mOnListener = mOnListener
            this.context = context
            this.uriList = uriList
        }


        override fun doInBackground(vararg params: String?): String? {

            val urlString = params[0]
            val data = params[1]

            val json = JSONObject(data)
            val record = json.getInt("id")
            val room = json.getInt("room")

//            var bitmap: Bitmap? = null
            var file: File? = null

            for (filePath in uriList!!) {
//                bitmap = BitmapFactory.decodeFile(filePath)
                file = File(filePath)
            }

            val fileBody: FileBody = FileBody(file, ContentType.DEFAULT_BINARY)
            val recordBody = StringBody(record.toString(), ContentType.MULTIPART_FORM_DATA)
            val roomBody = StringBody(room.toString(), ContentType.MULTIPART_FORM_DATA)

            val httpClient = DefaultHttpClient()
            val postRequest = HttpPost(urlString)

            val builder: MultipartEntityBuilder = MultipartEntityBuilder.create()
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
            builder.addPart("record", recordBody)
            builder.addPart("room", roomBody)
            builder.addPart("uri", fileBody)

            var entity = builder.build()
            postRequest.setEntity(entity)

            val response = httpClient.execute(postRequest)

            val reader = BufferedReader(
                InputStreamReader(
                    response.entity.content, "UTF-8"
                )
            )

            var sResponse: String?
            var s: StringBuilder = StringBuilder()

            sResponse = reader.readLine()
            while (sResponse != null) {
                s = s.append(sResponse)
                sResponse = reader.readLine()
            }

            println("------------------------------------------")
            println("Response:  " + s)
            println("------------------------------------------")

            if (!s.toString().equals(""))
                return s.toString()

            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if (!result.equals(""))
                mOnListener.onEnd(context, result)
//            if (result?.toInt() == 201) {
//                println("-------------------------------------------------------------------------")
//                println("-----------------       post onPostExecute(성공)      -------------------")
//                println("-------------------------------------------------------------------------")
//            } else {
//                println("-------------------------------------------------------------------------")
//                println("-----------------       post onPostExecute(실패)      -------------------")
//                println("-------------------------------------------------------------------------")
//            }
        }
    }

    // Post WriteRecord WR
    class postWRAsyncTask(
        mOnListener: WriteRecordContract.onRecordListener,
        context: Context,
        uriList: MutableList<String>?
    ) : AsyncTask<String, Any, String?>() {

        val mOnListener: WriteRecordContract.onRecordListener
        val context: Context
        val uriList: MutableList<String>?

        init {
            this.mOnListener = mOnListener
            this.context = context
            this.uriList = uriList
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

//                println("---------------------------------------")
//                println("연결주소 : $urlString")
//                println("응답코드 : $responseCode") // 200 201 등등
//                println("응답메세지 : $responseMessage") // Created
//                println("보낸 데이터 : $data")
//                println("---------------------------------------")

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
                println("-------------------------------------------------------------------------")
                println("-----------------       post onPostExecute(성공)      -------------------")
                println("-------------------------------------------------------------------------")
                mOnListener.onSuccess(context, result, uriList)
            } else {
                println("-------------------------------------------------------------------------")
                println("-----------------       post onPostExecute(실패)      -------------------")
                println("-------------------------------------------------------------------------")
                mOnListener.onFailure()
            }
        }
    }


    // POST Room
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
            if (result != null) {
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