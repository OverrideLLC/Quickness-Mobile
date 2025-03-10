package org.quickness.di

import com.data.impl.di.dataModule
import com.data.impl.di.nativeDatabase
import com.data.impl.di.repositoryDatabaseModule
import com.feature.viewModelModules
import com.network.impl.di.firebaseModule
import com.network.impl.di.repositoryNetworkModule
import com.network.impl.di.serviceModule
import com.shared.resources.interfaces.Resources
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module
import org.quickness.resources.ResourcesImpl

/**
 * Módulo principal de la aplicación que define las dependencias globales.
 *
 * Incluye la configuración de recursos de la aplicación.
 */
val appModule: Module = module {
    singleOf(::ResourcesImpl).bind(Resources::class)
}

/**
 * Initializes the Koin dependency injection container.
 *
 * This function starts Koin with the specified modules, providing a central place
 * to manage application dependencies.
 *
 * @param appDeclaration An optional lambda to further configure the Koin application.
 *                       This allows for additional setup or overrides during the
 *                       Koin initialization process. It's useful for testing or
 *                       customizing the dependency graph. If null, default
 *                       configuration will be used.
 *
 * The function configures the following modules:
 * - `appModule`: Core application dependencies.
 * - `viewModelsHome`: ViewModels related to the home feature.
 * - `viewModelsStart`: ViewModels related to the start feature.
 * - `serviceModule`: Dependencies for network services.
 * - `NativeModule`: Dependencies for native components.
 * - `firebaseModule`: Firebase-related dependencies.
 * - `repositoryNetworkModule`: Dependencies for network-related repositories.
 * - `viewModelModules`: General view model dependencies.
 * - `dataModule`: Data layer dependencies.
 * - `repositoryDatabaseModule`: Dependencies for database-related repositories.
 * - `nativeDatabase`: Dependencies for the native database.
 */
fun initKoin(appDeclaration: KoinAppDeclaration? = null) {
    startKoin {
        appDeclaration?.invoke(this)
        modules(
            appModule,
            viewModelsHome,
            viewModelsStart,
            serviceModule,
            NativeModule,
            firebaseModule,
            repositoryNetworkModule,
            viewModelModules,
            dataModule,
            repositoryDatabaseModule,
            nativeDatabase
        )
    }
}