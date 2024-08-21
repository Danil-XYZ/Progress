package com.example.progress.database

class ListRepository(private val listDao: ListDao) {

    fun getAllData() = listDao.readData()

    suspend fun deleteData(id: Long) {
        listDao.deleteData(id)
    }
}