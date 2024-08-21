package com.example.progress.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ListModel (
    val image: String,
    val listId: Long,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
) : Serializable