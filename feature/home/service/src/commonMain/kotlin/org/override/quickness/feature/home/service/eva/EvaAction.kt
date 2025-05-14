package org.override.quickness.feature.home.service.eva

sealed interface EvaAction {
    data object SendMessage : EvaAction
}