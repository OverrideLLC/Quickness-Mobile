package org.override.quickness.shared.utils.routes

sealed class RoutesWidget(val route: String) {
    object Widgets : RoutesWidget("widgets")
    object Lyra : RoutesWidget("lyra")
    object TaskTec : RoutesWidget("tasktec")
    object MindStack : RoutesWidget("mindstack")
}