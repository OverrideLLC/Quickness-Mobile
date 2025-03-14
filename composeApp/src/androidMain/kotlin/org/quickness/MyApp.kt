package org.quickness

import android.app.Application
import com.data.impl.di.dataModule
import com.data.impl.di.nativeDatabase
import com.data.impl.di.repositoryDatabaseModule
import com.feature.api.di.viewModelModulesHome
import com.feature.api.di.viewModelModulesSettings
import com.feature.api.di.viewModelModulesStart
import com.network.impl.di.firebaseModule
import com.network.impl.di.repositoryNetworkModule
import com.network.impl.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.logger.Level
import org.quickness.di.appModule
import org.quickness.di.initKoin
import org.quickness.di.nativeModule

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