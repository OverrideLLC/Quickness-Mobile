package com.shared.resources.interfaces

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

interface Resources {
    fun getDrawable(resource: String): DrawableResource
    fun getString(resource: String): StringResource
}