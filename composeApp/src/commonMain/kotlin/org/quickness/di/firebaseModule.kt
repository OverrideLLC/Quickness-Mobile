package org.quickness.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.quickness.data.remote.FirebaseService

/**
 * Módulo de Koin que proporciona la dependencia de [FirebaseService] para la autenticación
 * y otros servicios relacionados con Firebase.
 *
 * Este módulo registra una única instancia de [FirebaseService] como una dependencia de
 * la aplicación para que pueda ser inyectada en cualquier parte del código donde sea necesaria.
 */
val firebaseModule = module {
    // Registro de FirebaseService como una única instancia en la aplicación.
    singleOf(::FirebaseService)
}
