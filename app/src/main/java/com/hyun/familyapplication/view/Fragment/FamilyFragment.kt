package com.hyun.familyapplication.view.Fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.FamilyContract
import com.hyun.familyapplication.presenter.FamilyPresenter
import com.hyun.familyapplication.view.Activity.FindActivity
import com.hyun.familyapplication.view.Activity.TransferActivity
import com.hyun.familyapplication.view.Adapter.FamilyAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FamilyFragment : BaseFragment(), FamilyContract.View {

    private lateinit var mPresenter: FamilyPresenter
    private lateinit var recyclerView: RecyclerView
    private var adapter: FamilyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_family, container, false)

        val imgRight = view.findViewById<ImageView>(R.id.imgRightFamily)
        imgRight.setOnClickListener {
            Intent(context, FindActivity::class.java).let {
                startActivity(it)
            }
        }
        mPresenter.takeView(this)

        recyclerView = view.findViewById(R.id.recyclerview_family)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        adapter = FamilyAdapter()
        mPresenter.takeAdapter(adapter!!)
        adapter!!.setDialog(context!!)
        recyclerView.adapter = adapter

        return view
    }

    override fun initPresenter() {
        mPresenter = FamilyPresenter()
    }

    fun refreshData() {
        mPresenter.getFamily(context!!)
    }

    override fun onStart() {
        super.onStart()
        if (mPresenter == null) {
            initPresenter()
        }
        refreshData()
    }

    override fun onDestroy() {
        mPresenter.dropView()
        super.onDestroy()
    }


    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun transferActivity() {
        Intent(context, TransferActivity::class.java).let {
            startActivity(it)
        }
    }

    override fun showError(error: String) {
    }
}
