package org.quickness

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.logger.Level
import org.quickness.di.NativeModule
import org.quickness.di.appModule
import org.quickness.di.dataModule
import org.quickness.di.firebaseModule
import org.quickness.di.initKoin
import org.quickness.di.networkModule
import org.quickness.di.repositoryModule
import org.quickness.di.viewModelsModule

class MyApp : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApp)
            modules(
                appModule,
                dataModule,
                repositoryModule,
                viewModelsModule,
                networkModule,
                NativeModule,
                firebaseModule
            )
            printLogger()
        }
    }
}