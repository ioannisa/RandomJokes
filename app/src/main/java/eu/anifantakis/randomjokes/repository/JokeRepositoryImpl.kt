package eu.anifantakis.randomjokes.repository

import eu.anifantakis.randomjokes.Joke
import eu.anifantakis.randomjokes.database.JokeDao
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JokeRepositoryImpl(
    private val client: HttpClient,
    private val jokeDao: JokeDao
) : JokeRepository {
    private val baseUrl = "https://official-joke-api.appspot.com/"

    override suspend fun fetchAndStoreRandomJokes(): List<Joke> = withContext(Dispatchers.IO) {
        // Fetch jokes from the /random_ten endpoint
        val jokes: List<Joke> = client.get {
            url {
                takeFrom(baseUrl)
                encodedPath = "random_ten"
            }
        }.body()
        // Delete all cached jokes that are not favorites
        jokeDao.deleteNonFavoriteJokes()
        // Insert the newly fetched jokes into the cache
        jokeDao.upsertJokes(jokes)
        jokes
    }

    override fun getCachedJokes() = jokeDao.getAllJokes()

    override suspend fun markAsFavorite(joke: Joke) = withContext(Dispatchers.IO) {
        val updated = joke.copy(isFavorite = true)
        jokeDao.upsertJoke(updated)
    }

    override suspend fun markAsNotFavorite(joke: Joke) = withContext(Dispatchers.IO) {
        val updated = joke.copy(isFavorite = false)
        jokeDao.upsertJoke(updated)
    }
}