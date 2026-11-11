package com.sfj3861.veyra.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object VeyraColors {
    val BackgroundTop = Color(0xFF101522)
    val BackgroundBottom = Color(0xFF211A3D)
    val SurfaceGlass = Color(0x1FFFFFFF)
    val SurfaceGlassStrong = Color(0x24FFFFFF)
    val GlassBorder = Color(0x33FFFFFF)
    val Blue = Color(0xFF398BFF)
    val Violet = Color(0xFF9565F5)
    val TextPrimary = Color(0xFFF7F8FC)
    val TextSecondary = Color(0xFFADB8CA)
}

private val VeyraDarkScheme = darkColorScheme(
    primary = VeyraColors.Blue,
    secondary = VeyraColors.Violet,
    background = VeyraColors.BackgroundTop,
    surface = Color(0xFF202B40),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = VeyraColors.TextPrimary,
    onSurface = VeyraColors.TextPrimary
)

@Composable
fun VeyraTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = VeyraDarkScheme,
        content = content
    )
}
