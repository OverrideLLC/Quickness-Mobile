package org.quickness.interfaces.helpers

import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController

interface CheckPermissions {
    suspend fun checkPermissions(permissions: Permission, controller: PermissionsController)
}