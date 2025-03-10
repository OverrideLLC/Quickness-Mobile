package com.shared.resources.interfaces

import org.jetbrains.compose.resources.DrawableResource

interface ResourcesProvider {
    fun getDrawable(name: String): DrawableResource
}