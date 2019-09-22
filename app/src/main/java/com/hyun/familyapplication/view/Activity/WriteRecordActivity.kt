package com.hyun.familyapplication.view.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.WriteRecordContract
import com.hyun.familyapplication.model.Record
import com.hyun.familyapplication.presenter.WriteRecordPresenter
import kotlinx.android.synthetic.main.activity_write_record.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class WriteRecordActivity : BaseActivity(), WriteRecordContract.View {

    private lateinit var mPresenter: WriteRecordPresenter
    internal lateinit var db: DBHelper
    var id: Int = -1
    var record: Record? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_record)

        mPresenter.takeView(this)
        db = DBHelper(this, TABLE_NAME)

        setButton()

        val intent: Intent = intent
        if (intent.extras != null) {
            record = Record()
            val id: Int = intent.extras.getInt("id")
            val name: String = intent.extras.getString("name")
            val date: String = intent.extras.getString("date")
            val content: String = intent.extras.getString("content")

            record!!.id = id
            record!!.name = name
            record!!.date = date
            record!!.content = content

            edit_write_record.text = Editable.Factory.getInstance().newEditable(content)
        }
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initPresenter() {
        mPresenter = WriteRecordPresenter()
    }

    fun setButton() {
        image_back_write_record.setOnClickListener {
            onBackPressed()
        }
        text_save_write_record.setOnClickListener {
            val name: String // 작성자
            val date: String // 작성일
            val text: String // 작성내용
            // 이미지
            // 댓글

            // 작성자 (로그인 한 유저의 이름 or 장고 서버의 이름) : 장고 서버의 이름이 좋을 듯
            val user = FirebaseAuth.getInstance().currentUser
            name = user?.displayName.toString()
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
            text = edit_write_record.text.toString()

            // 내용 확인
            println("----------------------------------------")
            println("Name 값 : " + name)
            println("Date 값 : " + date)
            println("Text 값 : " + text)
            println("----------------------------------------")

        }
//        text_save_write_record.setOnClickListener {
//            if (record == null) {
//                val record = Record()
//                if (db.allRecord.size == 0)
//                    record.id = 0
//                else
//                    record.id = db.allRecord.last().id + 1
//                record.name = "백승현"
//                record.date = "2019년 8월 10일"
//                record.content = edit_write_record.text.toString()
//
//                mPresenter.saveRecord(this, db, record)
////            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
//            } else {
//                record!!.content = edit_write_record.text.toString()
//                mPresenter.updateRecord(this, db, record!!)
//            }
//            finish()
//        }

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


            //Add Imageview
            val imageView = ImageView(this@WriteRecordActivity)
            imageView.layoutParams =
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

            imageView.setImageURI(data?.data)
            linear_container_write_record.addView(imageView)

//            setContentView(linearLayout);
        }
    }

    override fun successRecord() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object {
        // image pick code
        private val IMAGE_PICK_CODE = 1000
        // Permission code
        private val PERMISSION_CODE = 1001

        private val TABLE_NAME = "Record"
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.dropView()
    }
}
