package eu.anifantakis.randomjokes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.anifantakis.randomjokes.Joke

@Database(entities = [Joke::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val jokeDao: JokeDao
}