package org.quickness.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.quickness.network.service.AuthUserService
import org.quickness.network.service.RegisterService
import org.quickness.network.service.TokensService

/**
 * Módulo de Koin que proporciona las dependencias de red necesarias para la aplicación.
 *
 * Este módulo registra los servicios que se encargan de la interacción con el backend para el registro
 * de usuarios y la obtención de tokens de acceso.
 */
val serviceModule = module {
    // Registro de RegisterService como una fábrica, creando una nueva instancia cada vez que se solicite.
    factoryOf(::RegisterService)

    // Registro de TokensService como una fábrica, creando una nueva instancia cada vez que se solicite.
    factoryOf(::TokensService)

    factoryOf(::AuthUserService)
}
