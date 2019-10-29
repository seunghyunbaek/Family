package com.hyun.familyapplication.view.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.MessageContract
import com.hyun.familyapplication.presenter.MessagePresenter
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : BaseActivity(), MessageContract.View {

    private var presenter:MessageContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        presenter?.takeView(this)


        message_fab.setOnClickListener{
            Intent(this, WriteMessageActivity::class.java).let{
                startActivity(it)
            }
        }
    }

    override fun initPresenter() {
        this.presenter = MessagePresenter()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        if(presenter==null)
            initPresenter()
    }

    override fun onDestroy() {
        presenter?.dropView()
        super.onDestroy()
    }
}
