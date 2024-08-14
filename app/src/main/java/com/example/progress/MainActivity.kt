package com.example.progress

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), Adapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView //
    private var adapter = Adapter(arrayListOf(), this) // Создаем новый адаптер с данными из dataList
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView) // Находим RecyclerView в макете


        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.allData.observe(this) { data ->
            data.let {
                adapter.updateData(data)
            }
        }
    }

    fun onAddClick(view: View) {
        startActivity(Intent(this, EditActivity::class.java))
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