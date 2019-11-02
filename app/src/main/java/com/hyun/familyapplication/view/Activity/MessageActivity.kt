package com.hyun.familyapplication.view.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.MessageContract
import com.hyun.familyapplication.model.Message
import com.hyun.familyapplication.presenter.MessagePresenter
import com.hyun.familyapplication.view.Adapter.MessageAdapter
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : BaseActivity(), MessageContract.View {

    private var presenter: MessagePresenter? = null
    private var adapter: MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        presenter?.takeView(this)

        recyclerview_message.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter()
        recyclerview_message.adapter = adapter

        message_fab.setOnClickListener {
            Intent(this, WriteMessageActivity::class.java).let {
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
        if (presenter == null) {
            initPresenter()
        } else {
            presenter?.getData(this)
        }
    }

    override fun onDestroy() {
        presenter?.dropView()
        super.onDestroy()
    }

    override fun setData(list: ArrayList<Message>) {
        adapter?.setData(list)
    }
}
