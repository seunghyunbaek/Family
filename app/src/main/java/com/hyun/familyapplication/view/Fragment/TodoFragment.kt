package com.hyun.familyapplication.view.Fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.model.Todo
import com.hyun.familyapplication.view.Activity.WriteTodoActivity
import com.hyun.familyapplication.view.Adapter.TodoAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class
TodoFragment : BaseFragment() {

    override fun initPresenter() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    lateinit var db: TodoDBHelper
    lateinit var db: DBHelper
    lateinit var mRecyclerView: RecyclerView
    var lstTodo: List<Todo> = ArrayList<Todo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_todo, container, false)

//        db = DBHelper(view.context, TABLE_NAME)
        db = DBHelper(view.context)
//        db = TodoDBHelper(context!!)

        val fab = view.findViewById<View>(R.id.fab_todo)
        fab.setOnClickListener {
            Intent(view.context, WriteTodoActivity::class.java).let {
                startActivity(it)
            }
        }

        mRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_todo)
        mRecyclerView.layoutManager = LinearLayoutManager(view.context)
        refreshData()

        Log.d("jkljkljkljkl", "TodoOnCreate()")

        return view
    }


    fun refreshData() {
        lstTodo = db.allTodo
        val adapter = TodoAdapter(context, lstTodo)
        mRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    companion object {
        private val TABLE_NAME = "Todo"
    }
}
