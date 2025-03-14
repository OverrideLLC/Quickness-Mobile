package org.quickness.resources

import com.shared.resources.interfaces.Resources
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.override

class ResourcesImpl : Resources {
    override fun getDrawable(resource: String): DrawableResource {
        return ResourceKey.valueOf(resource).drawable
    }

    override fun getString(resource: String): StringResource {
        return Res.string.override
    }
}