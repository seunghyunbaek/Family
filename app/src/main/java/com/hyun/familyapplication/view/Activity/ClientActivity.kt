//package com.hyun.familyapplication.view.Activity
//
//import android.Manifest
//import android.app.Activity
//import android.content.ContentValues
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.database.Cursor
//import android.graphics.BitmapFactory
//import android.net.Uri
//import android.os.AsyncTask
//import android.os.Build
//import android.os.Bundle
//import android.provider.MediaStore
//import android.widget.ImageView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityOptionsCompat
//import androidx.core.view.ViewCompat
//import com.bumptech.glide.Glide
//import com.google.firebase.auth.FirebaseAuth
//import com.hyun.familyapplication.R
//import cz.msebera.android.httpclient.HttpResponse
//import cz.msebera.android.httpclient.client.methods.HttpPost
//import cz.msebera.android.httpclient.entity.ContentType
//import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode
//import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder
//import cz.msebera.android.httpclient.entity.mime.content.FileBody
//import cz.msebera.android.httpclient.entity.mime.content.StringBody
//import cz.msebera.android.httpclient.impl.client.DefaultHttpClient
//import kotlinx.android.synthetic.main.activity_client.*
//import org.json.JSONObject
//import java.io.BufferedReader
//import java.io.File
//import java.io.InputStreamReader
//import java.io.OutputStreamWriter
//import java.lang.ref.WeakReference
//import java.net.HttpURLConnection
//import java.net.URL
//import java.net.URLEncoder
//
//class ClientActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_client)
//        iv_android_icon.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            val options = ActivityOptionsCompat
//                .makeSceneTransitionAnimation(
//                    this@ClientActivity, iv_android_icon,
//                    ViewCompat.getTransitionName(iv_android_icon)!!
//                )
//            startActivity(intent, options.toBundle())
//        }
//    }
//}



// =====================================================================================


package com.hyun.familyapplication.view.Activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.hyun.familyapplication.R
import cz.msebera.android.httpclient.HttpResponse
import cz.msebera.android.httpclient.client.methods.HttpPost
import cz.msebera.android.httpclient.entity.ContentType
import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder
import cz.msebera.android.httpclient.entity.mime.content.FileBody
import cz.msebera.android.httpclient.entity.mime.content.StringBody
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient
import kotlinx.android.synthetic.main.activity_client.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class ClientActivity : AppCompatActivity() {

    private lateinit var activityReference: WeakReference<ClientActivity>
    private lateinit var url: String
    private lateinit var uristring: Uri
    private lateinit var tempFile: File
    private var lst: ArrayList<File>? = ArrayList<File>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        activityReference = WeakReference(this@ClientActivity)
        url = getString(R.string.url)

        val mAuth = FirebaseAuth.getInstance()
        val mFirebaseUser = mAuth.currentUser
        if (mFirebaseUser != null) {
            val email = mFirebaseUser.email
            val name = mFirebaseUser.displayName

            edit_client_id.setText(email)
            edit_client_pw.setText(name)
        }

        tedPermission();

        btn_client_get.setOnClickListener {
            //            var json: JSONObject = JSONObject()
//            json.put("email", "test@gmail_com")
//            json.put("name", "testuser")
//            println("json test:" + json.toString())
//
////            val geturl = url + "users/qwe@gmail_com"
//            val geturl = url + "users/"
//            getAsyncTask(this@ClientActivity).execute(geturl, null, null)

            /* REST API GET 메서드 */
            val email = edit_client_id.text.toString().replace('.', '_')
            var getUrl = getString(R.string.url) + "users/" // 유저 데이터 조회 url
//            val spec = "a01093705783@gmail_com"
            if (!email.equals(""))
                getUrl = getUrl + email + "/"

            get2AsyncTask(this@ClientActivity).execute(getUrl, null, null)
        }

        btn_client_send.setOnClickListener {
            //            val email: String = edit_client_id.text.toString()
//            val name = edit_client_pw.text.toString()

//            val sendurl = url + "users/testuser@gmail_com/"
//
//            var json: JSONObject = JSONObject()
////            json.put("email", email)
////            json.put("name", name)
//            json.put("email", "test@test_com")
//            json.put("name", "teste")
//
//            var cv: MutableMap<String, String> = HashMap<String, String>()
//            cv.put("email", "testuser@gmail_com")
//            cv.put("name", "testtest")
//
//            var sbParams: StringBuffer = StringBuffer()
//            var sbParams2: String = ""
//            var key: String
//            var value: String
//
//            var reqParam4 = ""
//
//            var isAnd: Boolean = false
//
//            for (parameter in cv) {
//                key = parameter.key
//                value = parameter.value
//
//                if (isAnd) {
//                    sbParams.append("&")
//                    sbParams2 += "&"
//                    reqParam4 += "&"
//                }
//
//                sbParams.append(key).append("=").append(value)
//                sbParams2 = sbParams2 + key + "=" + value
//                reqParam4 = reqParam4 + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(
//                    value,
//                    "UTF-8"
//                )
//
//                if (!isAnd)
//                    if (cv.size >= 2)
//                        isAnd = true
//            }
//
//            var reqParam = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(
//                "testuser@gmail_com",
//                "UTF-8"
//            )
//            reqParam += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(
//                "tester",
//                "UTF-8"
//            )
//
//            var reqParam2 = URLEncoder.encode(sbParams.toString(), "UTF-8")
//            var reqParam3 = URLEncoder.encode(sbParams2.toString(), "UTF-8")

            /* REST API POST 메서드 */
            val email = edit_client_id.text.toString().replace('.', '_')
            val name = edit_client_pw.text.toString()

            val cv = ContentValues()
            cv.put("email", email)
            cv.put("name", name)
            val data = makeJson(cv)

            val getUrl = getString(R.string.url) + "users/"

            post2AsyncTask().execute(getUrl, data)
        }

        btn_client_put.setOnClickListener {
            /* REST API PUT 메서드 */
            val email = edit_client_id.text.toString().replace('.', '_')
            val name = edit_client_pw.text.toString()

            val cv = ContentValues()
            cv.put("email", email)
            cv.put("name", name)
            val data = makeJson(cv)

            val getUrl = getString(R.string.url) + "users/" + email + "/"

            putAsyncTask().execute(getUrl, data)
        }

        btn_client_del.setOnClickListener {
            /* REST API DELETE 메서드 */
            val email = edit_client_id.text.toString().replace('.', '_')
            val getUrl = getString(R.string.url) + "users/" + email + "/"

            delAsyncTask().execute(getUrl)
        }

        btn_google_data.setOnClickListener {
            val mAuth = FirebaseAuth.getInstance()
            val mFirebaseUser = mAuth.currentUser
            if (mFirebaseUser != null) {
                val email = mFirebaseUser.email
                val name = mFirebaseUser.displayName

                edit_client_id.setText(email)
                edit_client_pw.setText(name)
            }
        }

        btn_client_gallery.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    // permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    // show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    // permission already granted
                    pickImageFromGallery()
                }
            } else {
                // System OS is < Marshmallow
                pickImageFromGallery()
            }
        }

        btn_client_getimage.setOnClickListener {
            var getUrl = "http://172.30.1.13:8000/post/5/" // 유저 데이터 조회 url

            getImageAsyncTask(this@ClientActivity).execute(getUrl)
        }

        btn_client_setimage.setOnClickListener {
            var getUrl = "http://172.30.1.35:8000/blog/subimage/" // 유저 데이터 조회 url

            setImageAsyncTask(lst!!).execute(getUrl, tempFile.path)
        }
    }

    private fun getList(): ArrayList<File>? {
        return this!!.lst
    }

    private fun tedPermission() {

    }

    private fun pickImageFromGallery() {
        // intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            //Add Imageview
            val imageView = image_client
            uristring = data!!.data
            var photoUri = data.data
            var cursor: Cursor? = null

            try {
                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */

                val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)


                if (photoUri != null) {
                    cursor = getContentResolver().query(photoUri, proj, null, null, null);
                }
                if (cursor != null) {
                    val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                    cursor.moveToFirst();

                    tempFile = File(cursor.getString(column_index));
                    val f: File = File(tempFile.path)
                    lst?.add(tempFile)

                    val options: BitmapFactory.Options = BitmapFactory.Options();
                    val originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
                }

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }


//            bm = BitmapFactory.decodeFile(data?.data.toString())
            imageView.setImageURI(data?.data)
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER)
        }
    }

    private fun makeJson(contentValues: ContentValues): String {

        var isAnd = false
        var sbParams = ""

        for (parameter in contentValues.valueSet()) {
            var key = parameter.key
            var value = parameter.value.toString()

            // 파라미터가 두개 이상일때, 파라미터 사이에 &를 붙인다.
            if (isAnd)
                sbParams += "&"

            sbParams =
                sbParams + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8")

            // 파라미터가 2개 이상이면 isAnd를 true로 바꾸고 다음 루프부터 &를 붙인다.
            if (!isAnd)
                if (contentValues.size() >= 2)
                    isAnd = true
        }

        return sbParams
    }

    companion object {
        // image pick code
        private val IMAGE_PICK_CODE = 1000
        // Permission code
        private val PERMISSION_CODE = 1001

        // 이미지 테스트하기
        class getImageAsyncTask internal constructor(context: ClientActivity) :
            AsyncTask<String, Any, String?>() {

            private val activityReference: WeakReference<ClientActivity> = WeakReference(context)

            override fun doInBackground(vararg params: String?): String? {
                val url = params[0]
                println("--------------------------------------------------")
                println("$url")
                println("--------------------------------------------------")
                val obj = URL(url)
                with(obj.openConnection() as HttpURLConnection) {
                    // optional dfault is GET
                    requestMethod = "GET"
                    println("\nSending 'GET' request to URL : $url")
                    println("Response Code : $responseCode")

                    if (responseCode == 200) { // 성공 했을 때에만 데이터 읽어오기
                        BufferedReader(InputStreamReader(inputStream)).use {
                            var response = it.readText()
//                        Log.d("HttpClientActivity", response)
                            println(response)
                            return response
                        }
                    }
                }
                return null
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                val json = result
                val obj = JSONObject(result)
                println("---------------------------------------------")
                println(obj.get("cover"))
                println("---------------------------------------------")

                val activity = activityReference.get()
                Glide.with(activity!!).load(obj.get("cover"))
                    .into(activity.findViewById(R.id.image_client))
                Toast.makeText(activity, result, Toast.LENGTH_SHORT).show()
            }
        }

        fun kk(farray: ArrayList<File>) {
            var arr = farray
            if (arr.size > 0)
                arr.removeAt(0)
            if (!arr.isEmpty()) {
                println("=========================================================")
                println("     이미지 업로드 완료까지 남은 개수: ${arr.size}")
                println("=========================================================")
                setImageAsyncTask(arr).execute("url", arr.get(0).path)
            } else {
                println("=========================================================")
                println("           이미지 업로드 끝                               ")
                println("=========================================================")
            }
        }


        class setImageAsyncTask(lst: ArrayList<File>) : AsyncTask<String, Any, String?>() {

            var lst: ArrayList<File>

            init {
                this.lst = lst
            }

            override fun doInBackground(vararg params: String?): String? {
//                val url = params[0]
//                val url = "http://172.30.1.13:8000/users/"
                val url = "http://172.30.1.35:8000/blog/subimage/" // 유저 데이터 조회 url

                val jsonString = params[1]
                val uri = Uri.parse(jsonString)

                ////////////////////////////////////////////////////////

//                val bitmap = BitmapFactory.decodeFile(uri.path)

//                val bos: ByteArrayOutputStream = ByteArrayOutputStream()
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos)
//                val data: ByteArray = bos.toByteArray()

                val httpClient = DefaultHttpClient()
                val postRequest = HttpPost(url)

                val f: File = File(jsonString)

//                val bab: ByteArrayBody = ByteArrayBody(data, jsonString)
                val fileBody: FileBody = FileBody(f, ContentType.DEFAULT_BINARY)
                val stringbody1: StringBody =
                    StringBody("strbody@gmail_com", ContentType.MULTIPART_FORM_DATA)
                val stringbody2: StringBody = StringBody("strbody", ContentType.MULTIPART_FORM_DATA)
                val meeting: StringBody = StringBody(
                    "http://172.30.1.35:8000/blog/meeting/1/",
                    ContentType.MULTIPART_FORM_DATA
                )

                val builder: MultipartEntityBuilder = MultipartEntityBuilder.create()
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
//                builder.addPart("cover", bab)
//                builder.addPart("title", StringBody("title_6", ContentType.MULTIPART_FORM_DATA))
//                builder.addPart("email", stringbody1)
//                builder.addPart("name", stringbody1)
//                builder.addPart("title", stringbody1)
                builder.addPart("meeting", meeting)
                builder.addPart("path", fileBody)

//                val reqEntity: MultipartEntity =
//                    MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE)
//                reqEntity.addPart("cover", bab)
//                reqEntity.addPart("title", StringBody("title_5"))

                var entity = builder.build()
                postRequest.setEntity(entity)
                val response: HttpResponse = httpClient.execute(postRequest)
//                postRequest.setEntity(entity)
//                httpClient.execute(postRequest)

                val reader: BufferedReader = BufferedReader(
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

                ////////////////////////////////////////////////////////

                /*
                val mURL = URL(url)

                with(mURL.openConnection() as HttpURLConnection) {
                    requestMethod = "POST"
                    setRequestProperty("Accept-Charset", "UTF-8")
                    setRequestProperty("Context_Type", "application/json")

                    val wr = OutputStreamWriter(outputStream)
//                    wr.write(reqParam)
//                    wr.write(jsonString)
                    wr.flush()


                    println("URL : $url")
                    println("Response Code : $responseCode")
                    println("d $responseMessage")

                }
                */
                return null
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                println(result)
                kk(lst)
            }
        }

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

                    if (responseCode == 200) { // 성공 했을 때에만 데이터 읽어오기
                        BufferedReader(InputStreamReader(inputStream)).use {
                            var response = it.readText()
//                        Log.d("HttpClientActivity", response)
                            println(response)
                            return response
                        }
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
        class postAsyncTask : AsyncTask<String, Any, String?>() {
            override fun doInBackground(vararg params: String?): String? {

                val url = params[0]
//                val email = params[1]
//                val name = params[2]
                val jsonString = params[1]

//                println(jsonString)
//
                var reqParam = URLEncoder.encode(
                    "email",
                    "UTF-8"
                ) + "=" + URLEncoder.encode("testuser@gmail_com", "UTF-8")
                reqParam += "&" + URLEncoder.encode(
                    "name",
                    "UTF-8"
                ) + "=" + URLEncoder.encode("tester", "UTF-8")

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

        // GET
        class get2AsyncTask internal constructor(context: ClientActivity) :
            AsyncTask<String, Any, String?>() {

            private val activityReference: WeakReference<ClientActivity> = WeakReference(context)

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
                if (result != null)
                    Toast.makeText(activityReference.get(), "받은 데이터: $result", Toast.LENGTH_SHORT)
                        .show()
                else
                    Toast.makeText(
                        activityReference.get(),
                        "데이터 가져오기 실패",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }

        class post2AsyncTask : AsyncTask<String, Any, String?>() {
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
}
