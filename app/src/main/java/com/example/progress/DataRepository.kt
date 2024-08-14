package com.example.progress

class DataRepository(private val myDataDao: MyDataDao) {

    fun getAllData() = myDataDao.readData()

    suspend fun deleteData(id: Long) {
        myDataDao.deleteData(id)
    }
}