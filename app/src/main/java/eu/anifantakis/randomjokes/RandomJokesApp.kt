package eu.anifantakis.randomjokes

import android.app.Application
import eu.anifantakis.randomjokes.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RandomJokesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RandomJokesApp)
            modules(appModule)
        }
    }
}