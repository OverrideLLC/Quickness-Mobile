package org.quickness

import android.app.Application
import com.network.impl.firebaseModule
import com.network.impl.repositoryNetworkModule
import com.network.impl.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.logger.Level
import org.quickness.di.NativeModule
import org.quickness.di.appModule
import org.quickness.di.dataModule
import org.quickness.di.initKoin
import org.quickness.di.repositoryDatabaseModule
import org.quickness.di.viewModelsHome
import org.quickness.di.viewModelsSetting
import org.quickness.di.viewModelsStart

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
                viewModelsHome,
                viewModelsStart,
                viewModelsSetting,
                serviceModule,
                NativeModule,
                firebaseModule,
                repositoryNetworkModule,
            )
        }
    }
}