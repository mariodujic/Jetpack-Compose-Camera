package com.zero.camera.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SlideTopToBottomAnimation(animate: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = animate,
        enter = slideInVertically(
            initialOffsetY = { -700 },
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { -700 },
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearEasing
            )
        )
    ) { content() }
}