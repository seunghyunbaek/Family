package com.hyun.myapplication.view.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.hyun.myapplication.DBHelper.TestDBHelper
import com.hyun.myapplication.R
import com.hyun.myapplication.contract.WriteRecordContract
import com.hyun.myapplication.model.Record
import com.hyun.myapplication.presenter.WriteRecordPresenter
import kotlinx.android.synthetic.main.activity_write_record.*

class WriteRecordActivity : BaseActivity(), WriteRecordContract.View {

    private lateinit var wrPresenter: WriteRecordPresenter
    internal lateinit var db: TestDBHelper
    var id: Int = -1
    var record: Record? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_record)

        wrPresenter.takeView(this)
        db = TestDBHelper(this)

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

            etWR.text = Editable.Factory.getInstance().newEditable(content)
        }
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initPresenter() {
        wrPresenter = WriteRecordPresenter()
    }

    fun setButton() {
        btnBackWR.setOnClickListener {
            onBackPressed()
        }

        btnWriteWR.setOnClickListener {
            if (record == null) {
                val record = Record()
                record.id = db.allRecord.last().id + 1
                record.name = "백승현"
                record.date = "2019년 8월 10일"
                record.content = etWR.text.toString()

                wrPresenter.saveRecord(this, db, record)
//            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            } else {
                record!!.content = etWR.text.toString()
                wrPresenter.updateRecord(this, db, record!!)
            }
            finish()
        }

        btnGallaryWR.setOnClickListener {
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

        btnVideoWR.setOnClickListener {
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
//        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {


            //Add Imageview
            val imageView = ImageView(this@WriteRecordActivity)
            imageView.layoutParams =
                    ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

            imageView.setImageURI(data?.data)
            imageContainerWR.addView(imageView)

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
    }

    override fun onDestroy() {
        super.onDestroy()
        wrPresenter.dropView()
    }
}
