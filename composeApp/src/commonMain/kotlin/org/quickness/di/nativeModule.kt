package org.quickness.di

import org.koin.core.module.Module

/**
 * Declaración esperada de un módulo de Koin que se implementará en plataformas específicas.
 *
 * Esta propiedad se debe definir con la palabra clave `actual` en la implementación de cada plataforma
 * para proporcionar las dependencias específicas de la plataforma.
 *
 * Por ejemplo, en la implementación de Android, esta propiedad se podría definir como:
 *
 * actual val NativeModule: Module = module {
 *     // Configuración de dependencias específicas de Android
 * }
 */
expect val nativeModule: Module
