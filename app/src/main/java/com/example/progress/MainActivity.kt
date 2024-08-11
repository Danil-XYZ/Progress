package com.example.progress

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class MainActivity : AppCompatActivity(), Adapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView //
    private var list = arrayListOf<DataModel>()
    private var adapter = Adapter(list, this) // Создаем новый адаптер с данными из dataList
    val db by lazy { DataDatabase.getDatabase(this) }

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

        try {
            db.dataDao().readData().observe(this) {
                list.clear()
                if (it.isNotEmpty()) {
                    list.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }

        } catch (e: Exception) {
            Log.e("tesst", e.message.toString())
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

            list.removeAt(position)
            adapter.notifyItemRemoved(position)

            CoroutineScope(Dispatchers.IO).launch {
                delay(300)
                db.dataDao().deleteData(id)
            }

        } catch (e: Exception) {
            Log.e("tesst", e.toString())
        }
    }
}