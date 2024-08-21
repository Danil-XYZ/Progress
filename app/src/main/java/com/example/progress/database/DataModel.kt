package com.example.progress.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class DataModel (
    var date: String,
    var weight: String,
    var imageUri: String,
    var listId: Long = -1,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
) : Serializable