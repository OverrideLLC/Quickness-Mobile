package org.quickness.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.quickness.SharedPreference

/**
 * Módulo de Koin que proporciona las dependencias relacionadas con los datos de la aplicación.
 *
 * Este módulo registra las instancias de:
 * - [SharedPreference] para la gestión de preferencias compartidas.
 */
val dataModule = module {
    // Registro de SharedPreference para la gestión de preferencias de la aplicación
    singleOf(::SharedPreference)
}
