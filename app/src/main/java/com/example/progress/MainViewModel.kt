package com.example.progress

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.progress.database.DataDatabase
import com.example.progress.database.DataModel
import com.example.progress.database.DataRepository
import com.example.progress.database.ListDatabase
import com.example.progress.database.ListModel
import com.example.progress.database.ListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dataRepository: DataRepository
    private val listRepository: ListRepository

    init {
        val dataDao = DataDatabase.getDatabase(application).dataDao()
        val listDao = ListDatabase.getDatabase(application).listDao()
        dataRepository = DataRepository(dataDao)
        listRepository = ListRepository(listDao)
    }

    val allData: LiveData<List<DataModel>> = dataRepository.getAllData()
    val listData: LiveData<List<ListModel>> = listRepository.getAllData()

    fun deleteData(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.deleteData(id)
        }
    }

    fun deleteListData(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            listRepository.deleteData(id)
        }
    }

}