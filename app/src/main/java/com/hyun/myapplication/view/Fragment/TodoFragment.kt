package com.hyun.myapplication.view.Fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.DBHelper.TestDBHelper
import com.hyun.myapplication.DBHelper.TodoDBHelper
import com.hyun.myapplication.R
import com.hyun.myapplication.model.Todo
import com.hyun.myapplication.view.Activity.WriteTodoActivity
import com.hyun.myapplication.view.Adapter.TodoAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TodoFragment : Fragment() {

    lateinit var db: TodoDBHelper
    lateinit var mRecyclerView: RecyclerView
    var lstTodo: List<Todo> = ArrayList<Todo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_todo, container, false)

        db = TodoDBHelper(view.context)

        val fab = view.findViewById<View>(R.id.todoFAB)
        fab.setOnClickListener {
            Intent(view.context, WriteTodoActivity::class.java).let {
                startActivity(it)
            }
        }

        mRecyclerView = view.findViewById<RecyclerView>(R.id.todoRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(view.context)
        refreshData()

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
}
