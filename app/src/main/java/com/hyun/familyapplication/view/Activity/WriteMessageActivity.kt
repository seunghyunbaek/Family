package com.hyun.familyapplication.view.Activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.WriteMessageContract
import com.hyun.familyapplication.presenter.WriteMessagePresenter
import kotlinx.android.synthetic.main.activity_write_message.*

class WriteMessageActivity : BaseActivity(), WriteMessageContract.View {

    private var presenter:WriteMessagePresenter? = null
    private var possible:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_message)

        presenter?.takeView(this)

//        linear_group_find_user.setOnClickListener {//            Intent(this, Find2Activity::class.java).let {
//                startActivityForResult(it, 1)
//            }
//        }

        edit_email_wrmessage.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (s != null) {
//                    if (s.length > 0) {
//                        clear_wrmessage.visibility = View.VISIBLE
//                    }
//                }
                possible = false
                if (s?.length!! > 0) clear_wrmessage.visibility = View.VISIBLE
                else clear_wrmessage.visibility = View.INVISIBLE
            }
        })
        // 이메일 지우기
        clear_wrmessage.setOnClickListener {
            edit_email_wrmessage.text = null
        }

        btn_check_wrmessage.setOnClickListener {
            presenter?.checkReceiver(this, edit_email_wrmessage.text.toString())
        }

        // 메세지 보내기
        btn_send_wrmessage.setOnClickListener {

            // 수신인 작성확인
            if (edit_email_wrmessage.text.toString().equals("")) {
                showToast("수신인을 작성해주세요")
//                Toast.makeText(this, "메세지를 작성해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 메세지 작성확인
            if (edit_content_wrmessage.text.toString().equals("")) {
                showToast("메세지를 작성해주세요")
//                Toast.makeText(this, "메세지를 작성해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!possible) {
                showToast("수신인을 확인해주세요")
                return@setOnClickListener
            }

            // 메세지 보내기
            presenter?.sendMessage(this, edit_email_wrmessage.text.toString().replace(".", "_"), edit_content_wrmessage.text.toString().replace(".", "_"))
        }

    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun initPresenter() {
        presenter = WriteMessagePresenter()
    }

    override fun success() {
        finish()
    }

    override fun checkSuccess() {
        showToast("수신인이 확인되었습니다")
        possible = true
    }

    override fun checkFailure() {
        showToast("수신인을 다시 작성해주세요")
        possible = false
    }

    override fun showError(error: String) {
    }
}
