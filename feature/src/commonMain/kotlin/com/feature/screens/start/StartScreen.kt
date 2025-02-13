package com.feature.screens.start

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController
import com.feature.start.StartScreen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

@Composable
fun StartScreen(
    navController: NavController,
    title: StringResource,
    recurseButton1: Pair<StringResource, DrawableResource>,
    recurseButton2: Pair<StringResource, DrawableResource>,
    fontFamily: FontFamily,
    icon: DrawableResource
) = StartScreen(
    navController = navController,
    title = title,
    recurseButton1 = recurseButton1,
    recurseButton2 = recurseButton2,
    fontFamily = fontFamily,
    icon = icon
)