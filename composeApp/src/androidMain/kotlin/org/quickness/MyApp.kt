package org.quickness

import android.app.Application
import com.data.impl.di.dataModule
import com.data.impl.di.nativeDatabase
import com.data.impl.di.repositoryDatabaseModule
import com.network.impl.di.firebaseModule
import com.network.impl.di.repositoryNetworkModule
import com.network.impl.di.serviceModule
import com.quickness.shared.utils.providers.ContextProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.logger.Level
import org.quickness.di.NativeModule
import org.quickness.di.appModule
import org.quickness.di.initKoin
import org.quickness.di.viewModelsHome
import org.quickness.di.viewModelsSetting
import org.quickness.di.viewModelsStart

class MyApp : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        ContextProvider.initialize(this@MyApp.applicationContext)
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
                nativeDatabase
            )
        }
    }
}