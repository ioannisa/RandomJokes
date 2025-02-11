package eu.anifantakis.randomjokes.repository

import eu.anifantakis.randomjokes.Joke
import kotlinx.coroutines.flow.Flow

interface JokeRepository {
    suspend fun fetchAndStoreRandomJokes(): List<Joke>
    fun getCachedJokes(): Flow<List<Joke>>
    suspend fun markAsFavorite(joke: Joke)
}