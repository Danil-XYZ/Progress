package com.example.progress

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class DataModel (
    var date: String,
    var weight: String,
    var imageUri: String,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
) : Serializable