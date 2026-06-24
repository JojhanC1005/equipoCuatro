
package com.example.pico_botella_grupo4.repository

import com.example.pico_botella_grupo4.data.ChallengeDao
import com.example.pico_botella_grupo4.model.Challenge

class ChallengeRepository(
    private val dao: ChallengeDao
) {

    fun getAllChallenges() =
        dao.getAllChallenges()

    suspend fun insert(challenge: Challenge) {
        dao.insert(challenge)
    }

    suspend fun update(challenge: Challenge) {
        dao.update(challenge)
    }

    suspend fun delete(challenge: Challenge) {
        dao.delete(challenge)
    }

    suspend fun getById(id: Int) =
        dao.getById(id)

    suspend fun getRandomChallenge() =
        dao.getRandomChallenge()
}