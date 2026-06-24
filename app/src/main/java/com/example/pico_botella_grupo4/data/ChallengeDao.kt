package com.example.pico_botella_grupo4.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pico_botella_grupo4.model.Challenge

@Dao
interface ChallengeDao {

    @Insert
    suspend fun insert(challenge: Challenge)

    @Update
    suspend fun update(challenge: Challenge)

    @Delete
    suspend fun delete(challenge: Challenge)

    @Query("SELECT * FROM challenges ORDER BY id DESC")
    fun getAllChallenges(): LiveData<List<Challenge>>

    @Query("SELECT * FROM challenges WHERE id = :id")
    suspend fun getById(id: Int): Challenge?

    @Query("SELECT * FROM challenges ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomChallenge(): Challenge?
}