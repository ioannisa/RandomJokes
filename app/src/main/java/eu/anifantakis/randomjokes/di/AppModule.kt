package eu.anifantakis.randomjokes.di

import androidx.room.Room
import eu.anifantakis.randomjokes.database.AppDatabase
import eu.anifantakis.randomjokes.repository.JokeRepository
import eu.anifantakis.randomjokes.repository.JokeRepositoryImpl
import eu.anifantakis.randomjokes.screens.JokeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    //  Ktor HttpClient
    single {
        HttpClient(CIO) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

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

    viewModelOf(::JokeViewModel)
}