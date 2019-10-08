package com.hyun.familyapplication.view.Activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.WriteRecordContract
import com.hyun.familyapplication.model.Record
import com.hyun.familyapplication.presenter.WriteRecordPresenter
import kotlinx.android.synthetic.main.activity_write_record.*
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class WriteRecordActivity : BaseActivity(), WriteRecordContract.View, View.OnClickListener {

    private lateinit var mPresenter: WriteRecordPresenter
    internal lateinit var db: DBHelper
    var id: Int = -1
    var record: Record? = null
    var uriList: MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_record)
        uriList = ArrayList<String>()

        mPresenter.takeView(this)

        db = DBHelper(this)

        image_back_write_record.setOnClickListener(this)
        text_save_write_record.setOnClickListener(this)
        image_gallary_write_record.setOnClickListener(this)
        image_video_write_record.setOnClickListener(this)


        // 넘어오는 데이터가 있으면 넘어온 데이터 뷰에 뿌려주기
//        val intent: Intent = intent
//        if (intent.extras != null) {
//            record = Record()
//            val id: Int = intent.extras.getInt("id")
//            val name: String = intent.extras.getString("name")
//            val date: String = intent.extras.getString("date")
//            val content: String = intent.extras.getString("content")
//
//            record!!.name = name
//            record!!.date = date
//            record!!.content = content
//
//            edit_write_record.text = Editable.Factory.getInstance().newEditable(content)
//        }
    }

    override fun onDestroy() {
        mPresenter.dropView()
        super.onDestroy()
    }

    override fun initPresenter() {
        mPresenter = WriteRecordPresenter()
    }

    override fun showError(error: String) {
    }

    override fun showLoading() {
        progress_write_record.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_write_record.visibility = View.GONE
    }

    private fun pickImageFromGallery() {
        // intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    // permission from popup granted
                    pickImageFromGallery()
                } else {
                    // permission from popup denied
                    Toast.makeText(
                        this@WriteRecordActivity,
                        "Permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            val uri = data?.data
            //Add Imageview
            val imageView = ImageView(this@WriteRecordActivity)
            val height = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                240f,
                resources.displayMetrics
            ).toInt()
            imageView.layoutParams =
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    height
                )

            val margin: ViewGroup.MarginLayoutParams =
                ViewGroup.MarginLayoutParams(imageView.layoutParams)
            margin.setMargins(0, 20, 0, 20)
            imageView.layoutParams = margin
            imageView.setImageURI(uri)
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER)

            var cursor: Cursor? = null
            val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
            if (uri != null) {
                cursor = getContentResolver().query(uri, proj, null, null, null);
            }
            if (cursor != null) {
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                val tempFile = File(cursor.getString(column_index));
                uriList?.add(tempFile.absolutePath)
            }

            if (cursor != null) {
                cursor.close();
            }

//            uriList?.add(data?.data.toString())
            linear_container_write_record.addView(imageView)
//            setContentView(linearLayout);
        }
    }

    override fun successRecord() {
//        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object {
        // image pick code
        private val IMAGE_PICK_CODE = 1000
        // Permission code
        private val PERMISSION_CODE = 1001
    }

    fun setButton() {
//        image_back_write_record.setOnClickListener {
//            onBackPressed()
//        }
        text_save_write_record.setOnClickListener {
            val date: String // 작성일
            val content: String // 작성내용

            // 작성일
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var current = LocalDate.now()
                var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                var formatted = current.format(formatter)
                date = formatted
//                println("API Level 26 이상 : " + formatted)
            } else {
                var simpleDateFormatter = SimpleDateFormat("yyyy-MM-dd")
                var currentDate = simpleDateFormatter.format(Date())
                date = currentDate
//                println("API Level 1 이상 : " + currentDate)
            }
            // 작성내용
            content = edit_write_record.text.toString()
            val contentValues = ContentValues()
            contentValues.put("date", date)
            contentValues.put("content", content)
            mPresenter.saveRecordInServer(this, contentValues, uriList)
            // 내용 확인
//            println("----------------------------------------")
//            println("Name 값 : " + name)
//            println("Date 값 : " + date)
//            println("Text 값 : " + text)
//            println("----------------------------------------")
        }

        image_gallary_write_record.setOnClickListener {
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

        image_video_write_record.setOnClickListener {
            finish()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            // 뒤로가기
            R.id.image_back_write_record -> {
                onBackPressed()
            }
            // 저장하기
            R.id.text_save_write_record -> {
                val date: String // 작성일
                val content: String // 작성내용

                // 작성일
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    var current = LocalDate.now()
                    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    var formatted = current.format(formatter)
                    date = formatted
//                println("API Level 26 이상 : " + formatted)
                } else {
                    var simpleDateFormatter = SimpleDateFormat("yyyy-MM-dd")
                    var currentDate = simpleDateFormatter.format(Date())
                    date = currentDate
//                println("API Level 1 이상 : " + currentDate)
                }
                // 작성내용
                content = edit_write_record.text.toString()
                val contentValues = ContentValues()
                contentValues.put("date", date)
                contentValues.put("content", content)
                showLoading()
                mPresenter.saveRecordInServer(this, contentValues, uriList)
            }
            R.id.image_gallary_write_record -> {
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
            R.id.image_video_write_record -> {

            }
        }
    }
}
