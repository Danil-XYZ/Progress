package com.example.progress.database;

import androidx.lifecycle.LiveData
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query
import androidx.room.Update

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addData(list: ListModel)

    @Update
    fun updateData(list: ListModel)

    @Query("SELECT * FROM ListModel ORDER BY id") // ASC - вопрос
    fun readData(): LiveData<List<ListModel>>

    @Query("DELETE FROM ListModel WHERE id=:id")
    suspend fun deleteData(id: Long)
}
