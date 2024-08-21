package com.example.progress.database

class DataRepository(private val myDataDao: MyDataDao) {

    fun getAllData() = myDataDao.readData()

    suspend fun deleteData(id: Long) {
        myDataDao.deleteData(id)
    }
}