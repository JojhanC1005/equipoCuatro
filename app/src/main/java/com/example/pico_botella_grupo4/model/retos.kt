package com.example.pico_botella_grupo4.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenges")
data class Challenge(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val description: String
)