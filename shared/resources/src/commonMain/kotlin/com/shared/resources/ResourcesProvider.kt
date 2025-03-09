package com.shared.resources

import org.jetbrains.compose.resources.DrawableResource

interface ResourcesProvider {
    fun getDrawable(name: String): DrawableResource
}