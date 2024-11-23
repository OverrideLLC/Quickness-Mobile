package org.quickness.ui.animations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object NavAnimations {
    val enterTransition =
        slideInHorizontally(initialOffsetX = { it }) + fadeIn(animationSpec = tween(500))
    val exitTransition =
        slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(animationSpec = tween(500))
}