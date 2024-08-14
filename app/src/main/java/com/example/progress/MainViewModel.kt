package com.example.progress

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DataRepository

    init {
        val dataDao = DataDatabase.getDatabase(application).dataDao()
        repository = DataRepository(dataDao)
    }

    val allData: LiveData<List<DataModel>> = repository.getAllData()

    fun deleteData(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(id)
        }
    }

}