package com.example.progress

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit


class MainActivity : AppCompatActivity() {


    private val viewModel: MainViewModel by viewModels()
    private lateinit var recyclerViewFragment: RecyclerViewFragment
    private lateinit var listFragment: ListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Создаем и добавляем RecyclerViewFragment в контейнер
        recyclerViewFragment = RecyclerViewFragment()
        recyclerViewFragment.setViewModel(viewModel)

        listFragment = ListFragment()
        supportFragmentManager.commit {
            replace(R.id.fragment_container, listFragment)
        }

        // Подписываемся на изменения данных и обновляем фрагмент
        viewModel.allData.observe(this) { data ->
            recyclerViewFragment.updateData(data)
        }


        viewModel.listData.observe(this) { data ->
            listFragment.updateData(data)
        }
    }

    fun onAddClick(view: View) {
        startActivity(Intent(this, EditActivity::class.java))
    }


}