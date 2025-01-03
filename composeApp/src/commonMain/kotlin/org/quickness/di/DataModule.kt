package org.quickness.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.quickness.SharedPreference
import org.quickness.data.model.User
import org.quickness.utils.`object`.KeysCache.UID_KEY

/**
 * Módulo de Koin que proporciona las dependencias relacionadas con los datos de la aplicación.
 *
 * Este módulo registra las instancias de:
 * - [SharedPreference] para la gestión de preferencias compartidas.
 */
val dataModule = module {
    val uid = SharedPreference().getString(UID_KEY, "")
    // Registro de SharedPreference para la gestión de preferencias de la aplicación
    singleOf(::SharedPreference)
    single {
        User(
            uid = uid
        )
    }
}
