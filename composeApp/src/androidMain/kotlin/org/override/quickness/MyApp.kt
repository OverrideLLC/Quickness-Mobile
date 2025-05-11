package org.override.quickness

import android.app.Application
import org.override.quickness.data.impl.di.dataModule
import org.override.quickness.data.impl.di.nativeDatabase
import org.override.quickness.data.impl.di.repositoryDatabaseModule
import org.override.quickness.feature.api.di.viewModelModulesHome
import org.override.quickness.feature.api.di.viewModelModulesSettings
import org.override.quickness.feature.api.di.viewModelModulesStart
import org.override.quickness.network.impl.di.firebaseModule
import org.override.quickness.network.impl.di.repositoryNetworkModule
import org.override.quickness.network.impl.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.logger.Level
import org.override.quickness.di.appModule
import org.override.quickness.di.initKoin
import org.override.quickness.di.nativeModule

class MyApp : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApp)
            printLogger()
            modules(
                appModule,
                dataModule,
                repositoryDatabaseModule,
                viewModelModulesHome,
                viewModelModulesSettings,
                viewModelModulesStart,
                serviceModule,
                nativeModule,
                firebaseModule,
                repositoryNetworkModule,
                nativeDatabase
            )
        }
    }
}