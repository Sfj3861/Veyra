package com.sfj3861.veyra.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Serializable-by-fields theme definition. Colors are stored as #RRGGBB strings
 * so user themes can later share the same stable representation.
 */
data class VeyraThemeConfig(
    val id: String,
    val displayName: String,
    val backgroundTop: String,
    val backgroundBottom: String,
    val surfaceGlass: String,
    val surfaceGlassStrong: String,
    val glassBorder: String,
    val primary: String,
    val secondary: String,
    val textPrimary: String,
    val textSecondary: String,
    val glassOpacity: Float = 0.12f,
    val borderOpacity: Float = 0.20f
) {
    fun toPalette(): VeyraPalette = VeyraPalette(
        backgroundTop = colorFromHex(backgroundTop),
        backgroundBottom = colorFromHex(backgroundBottom),
        surfaceGlass = colorFromHex(surfaceGlass).copy(alpha = glassOpacity.coerceIn(0f, 1f)),
        surfaceGlassStrong = colorFromHex(surfaceGlassStrong).copy(alpha = (glassOpacity + 0.06f).coerceIn(0f, 1f)),
        glassBorder = colorFromHex(glassBorder).copy(alpha = borderOpacity.coerceIn(0f, 1f)),
        blue = colorFromHex(primary),
        violet = colorFromHex(secondary),
        textPrimary = colorFromHex(textPrimary),
        textSecondary = colorFromHex(textSecondary)
    )
}

data class VeyraPalette(
    val backgroundTop: Color,
    val backgroundBottom: Color,
    val surfaceGlass: Color,
    val surfaceGlassStrong: Color,
    val glassBorder: Color,
    val blue: Color,
    val violet: Color,
    val textPrimary: Color,
    val textSecondary: Color
)

object VeyraThemePresets {
    val Glassmorphism = VeyraThemeConfig(
        id = "glassmorphism",
        displayName = "Glassmorphism",
        backgroundTop = "#101522",
        backgroundBottom = "#211A3D",
        surfaceGlass = "#FFFFFF",
        surfaceGlassStrong = "#FFFFFF",
        glassBorder = "#FFFFFF",
        primary = "#398BFF",
        secondary = "#9565F5",
        textPrimary = "#F7F8FC",
        textSecondary = "#ADB8CA",
        glassOpacity = 0.12f,
        borderOpacity = 0.20f
    )

    val Minimal = VeyraThemeConfig(
        id = "minimal",
        displayName = "Minimalista",
        backgroundTop = "#17191D",
        backgroundBottom = "#202329",
        surfaceGlass = "#343941",
        surfaceGlassStrong = "#414752",
        glassBorder = "#AAB4C3",
        primary = "#D8E2F0",
        secondary = "#A8B8D0",
        textPrimary = "#F5F7FA",
        textSecondary = "#B8C0CC",
        glassOpacity = 0.72f,
        borderOpacity = 0.16f
    )

    val Futuristic = VeyraThemeConfig(
        id = "futuristic",
        displayName = "Futurista",
        backgroundTop = "#07131F",
        backgroundBottom = "#17103A",
        surfaceGlass = "#12364A",
        surfaceGlassStrong = "#1B4A62",
        glassBorder = "#3FE4F5",
        primary = "#36D9F5",
        secondary = "#B45CFF",
        textPrimary = "#F1FCFF",
        textSecondary = "#9CC8D7",
        glassOpacity = 0.42f,
        borderOpacity = 0.42f
    )

    val MaterialYou = VeyraThemeConfig(
        id = "material-you",
        displayName = "Material You",
        backgroundTop = "#1B1B24",
        backgroundBottom = "#292536",
        surfaceGlass = "#51465F",
        surfaceGlassStrong = "#62546F",
        glassBorder = "#D8C4E8",
        primary = "#D0BCFF",
        secondary = "#E8B9D5",
        textPrimary = "#F7F0FA",
        textSecondary = "#D0C4D7",
        glassOpacity = 0.48f,
        borderOpacity = 0.24f
    )

    val all: List<VeyraThemeConfig> =
        listOf(Glassmorphism, Minimal, Futuristic, MaterialYou)

    fun byId(id: String?): VeyraThemeConfig =
        all.firstOrNull { it.id == id } ?: Glassmorphism
}

/** Accepts #RGB, #RRGGBB and #AARRGGBB. Invalid values fall back safely. */
fun colorFromHex(value: String): Color {
    val raw = value.trim().removePrefix("#")
    return runCatching {
        when (raw.length) {
            3 -> Color(
                red = raw[0].digitToInt(16) / 15f,
                green = raw[1].digitToInt(16) / 15f,
                blue = raw[2].digitToInt(16) / 15f
            )
            6 -> Color(android.graphics.Color.parseColor("#$raw"))
            8 -> Color(android.graphics.Color.parseColor("#$raw"))
            else -> Color.Unspecified
        }
    }.getOrElse { Color.Unspecified }.let {
        if (it == Color.Unspecified) Color(0xFF398BFF) else it
    }
}
