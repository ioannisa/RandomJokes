package eu.anifantakis.randomjokes.di

import androidx.room.Room
import eu.anifantakis.randomjokes.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
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

    //

}