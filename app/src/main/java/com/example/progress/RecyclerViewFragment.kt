package com.example.progress

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progress.database.DataModel

class RecyclerViewFragment : Fragment(), Adapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private var adapter = Adapter(arrayListOf(), this)
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        return view
    }

    fun updateData(newData: List<DataModel>) {
        adapter.updateData(newData)
    }

    fun setViewModel(viewModel: MainViewModel) {
        this.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()

        viewModel.allData.observe(this) { data ->
            data.let {
                adapter.updateData(data.reversed())
            }
        }
    }



    override fun onEditClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onDeleteClick(position: Int) {
        try {
            Log.e("tesst", "pos $position")
            Log.e("tesst", "id ${adapter.getItemId(position)}")

            val id = adapter.getItemId(position)
            adapter.removeItem(position)
            viewModel.deleteData(id)

        } catch (e: Exception) {
            Log.e("tesst", e.toString())
        }
    }
}