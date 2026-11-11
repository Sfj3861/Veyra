package com.sfj3861.veyra.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DefaultPalette = VeyraThemePresets.Glassmorphism.toPalette()
private val LocalVeyraPalette = staticCompositionLocalOf { DefaultPalette }

/** Theme colors consumed by the existing UI and future customizable components. */
object VeyraColors {
    val BackgroundTop: Color @Composable get() = LocalVeyraPalette.current.backgroundTop
    val BackgroundBottom: Color @Composable get() = LocalVeyraPalette.current.backgroundBottom
    val SurfaceGlass: Color @Composable get() = LocalVeyraPalette.current.surfaceGlass
    val SurfaceGlassStrong: Color @Composable get() = LocalVeyraPalette.current.surfaceGlassStrong
    val GlassBorder: Color @Composable get() = LocalVeyraPalette.current.glassBorder
    val Blue: Color @Composable get() = LocalVeyraPalette.current.blue
    val Violet: Color @Composable get() = LocalVeyraPalette.current.violet
    val TextPrimary: Color @Composable get() = LocalVeyraPalette.current.textPrimary
    val TextSecondary: Color @Composable get() = LocalVeyraPalette.current.textSecondary
}

@Composable
fun VeyraTheme(
    config: VeyraThemeConfig = VeyraThemePresets.Glassmorphism,
    content: @Composable () -> Unit
) {
    val palette = config.toPalette()
    val colorScheme = darkColorScheme(
        primary = palette.blue,
        secondary = palette.violet,
        background = palette.backgroundTop,
        surface = palette.surfaceGlassStrong,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = palette.textPrimary,
        onSurface = palette.textPrimary
    )

    CompositionLocalProvider(LocalVeyraPalette provides palette) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
