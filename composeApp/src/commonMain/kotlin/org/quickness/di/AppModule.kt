package org.quickness.di

import com.data.impl.di.*
import com.feature.api.di.viewModelModulesHome
import com.feature.api.di.viewModelModulesService
import com.feature.api.di.viewModelModulesSettings
import com.feature.api.di.viewModelModulesStart
import com.network.impl.di.*
import com.shared.resources.interfaces.Resources
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module
import org.quickness.AppViewModel
import org.quickness.resources.ResourcesImpl

/**
 * Módulo principal de la aplicación que define las dependencias globales.
 *
 * Incluye la configuración de recursos de la aplicación.
 */
val appModule: Module = module {
    singleOf(::ResourcesImpl).bind(Resources::class)
    viewModelOf(::AppViewModel)
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
 * - `serviceModule`: Dependencies for network services.
 * - `nativeModule`: Dependencies for native components.
 * - `nativeDatabase`: Dependencies for the native database.
 * - `firebaseModule`: Firebase-related dependencies.
 * - `dataModule`: Data layer dependencies.
 * - `repositoryNetworkModule`: Dependencies for network-related repositories.
 * - `repositoryDatabaseModule`: Dependencies for database-related repositories.
 * - `viewModelModulesStart`: ViewModels related to the start feature.
 * - `viewModelModulesHome`: ViewModels related to the home feature.
 */
fun initKoin(appDeclaration: KoinAppDeclaration? = null) {
    startKoin {
        appDeclaration?.invoke(this)
        modules(
            appModule,
            serviceModule,
            nativeModule,
            nativeDatabase,
            firebaseModule,
            dataModule,
            repositoryNetworkModule,
            repositoryDatabaseModule,
            viewModelModulesStart,
            viewModelModulesHome,
            viewModelModulesSettings,
            viewModelModulesService
        )
    }
}