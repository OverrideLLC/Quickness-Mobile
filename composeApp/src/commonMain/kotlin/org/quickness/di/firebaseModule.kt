package org.quickness.di

import org.koin.dsl.module
import org.quickness.data.remote.FirebaseService

val firebaseModule = module {
    single { FirebaseService() }
}