package org.quickness.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.quickness.QRCodeGeneratorImpl
import org.quickness.SharedPreference
import org.quickness.interfaces.QRCodeGenerator

/**
 * Módulo de Koin que proporciona las dependencias relacionadas con los datos de la aplicación.
 *
 * Este módulo registra las instancias de:
 * - [QRCodeGenerator] para la generación de códigos QR.
 * - [SharedPreference] para la gestión de preferencias compartidas.
 */
val dataModule = module {
    // Registro de la implementación de la interfaz QRCodeGenerator
    singleOf<QRCodeGenerator>(::QRCodeGeneratorImpl)

    // Registro de SharedPreference para la gestión de preferencias de la aplicación
    singleOf(::SharedPreference)
}
