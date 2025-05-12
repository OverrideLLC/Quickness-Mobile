package org.override.quickness.feature.home.service.eva

sealed interface EvaAction {
    object OnBack: EvaAction
}