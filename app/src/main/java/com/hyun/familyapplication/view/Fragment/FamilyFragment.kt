package com.hyun.familyapplication.view.Fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import com.hyun.familyapplication.view.Activity.MainActivity
import com.hyun.familyapplication.view.Adapter.FamilyAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FamilyFragment : BaseFragment() {

    lateinit var invitePeople: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_family, container, false)

        invitePeople = view.findViewById(R.id.linear_group_invite_family)
        invitePeople.setOnClickListener {
            Intent(view.context, MainActivity::class.java).let {
                startActivity(it)
            }
        }

        val mRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_family)
        mRecyclerView.layoutManager = LinearLayoutManager(view.context)
        mRecyclerView.adapter = FamilyAdapter()

        return view
    }


    override fun initPresenter() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
