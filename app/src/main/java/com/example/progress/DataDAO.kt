package com.example.progress

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DataDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addData(data: DataModel)

    @Update
    fun updateData(data: DataModel)

    @Query("SELECT * FROM DataModel ORDER BY id") // ASC - вопрос
    fun readData(): LiveData<List<DataModel>>

    @Query("DELETE FROM DataModel WHERE id=:id")
    fun deleteData(id: Long)

}