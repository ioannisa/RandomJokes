package eu.anifantakis.randomjokes.di

import androidx.room.Room
import eu.anifantakis.randomjokes.database.AppDatabase
import eu.anifantakis.randomjokes.repository.JokeRepository
import eu.anifantakis.randomjokes.repository.JokeRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    // Database DI
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "jokes_database.db"
        ).build()
    }
    single { get<AppDatabase>().jokeDao }

    // Repository
    singleOf(::JokeRepositoryImpl).bind<JokeRepository>()

}