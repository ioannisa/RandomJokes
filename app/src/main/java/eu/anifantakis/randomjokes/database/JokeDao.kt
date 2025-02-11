package eu.anifantakis.randomjokes.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import eu.anifantakis.randomjokes.Joke
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {
    @Upsert
    suspend fun upsertJoke(joke: Joke)

    @Upsert
    suspend fun upsertJokes(jokes: List<Joke>)

    @Query("SELECT * FROM Joke ORDER BY fetchedAt DESC")
    fun getAllJokes(): Flow<List<Joke>>

    @Query("SELECT * FROM Joke WHERE isFavorite = 1 ORDER BY fetchedAt DESC")
    fun getFavoriteJokes(): Flow<List<Joke>>

    @Query("DELETE FROM Joke WHERE uid = :id")
    suspend fun deleteJoke(id: Int)

    @Query("DELETE FROM Joke")
    suspend fun deleteAllJokes()

    @Query("DELETE FROM Joke WHERE isFavorite = 0")
    suspend fun deleteNonFavoriteJokes()
}