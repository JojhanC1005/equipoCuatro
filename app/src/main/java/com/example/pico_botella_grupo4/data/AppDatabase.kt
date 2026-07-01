package com.example.pico_botella_grupo4.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pico_botella_grupo4.model.Challenge

@Database(
    entities = [Challenge::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun challengeDao(): ChallengeDao
}