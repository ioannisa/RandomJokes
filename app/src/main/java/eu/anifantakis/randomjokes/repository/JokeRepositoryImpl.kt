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
        // Fetch list of jokes from the remote /random_ten endpoint
        val jokes: List<Joke> = client.get {
            url {
                takeFrom(baseUrl)
                encodedPath = "random_ten"
            }
        }.body()
        // Save (upsert) the jokes into the database
        jokeDao.upsertJokes(jokes = jokes)
        jokes
    }

    override fun getCachedJokes() = jokeDao.getAllJokes()

    override suspend fun markAsFavorite(joke: Joke) = withContext(Dispatchers.IO) {
        val updated = joke.copy(isFavorite = true)
        jokeDao.upsertJoke(updated)
    }
}