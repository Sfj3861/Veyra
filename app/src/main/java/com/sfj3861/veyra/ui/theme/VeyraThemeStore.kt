package com.sfj3861.veyra.ui.theme

import android.content.Context

/**
 * Small local persistence layer for the active theme.
 * SharedPreferences keeps this first milestone dependency-free.
 */
object VeyraThemeStore {
    private const val PREFS = "veyra_theme_preferences"
    private const val KEY_THEME_ID = "theme_id"
    private const val KEY_CUSTOM_CONFIG = "custom_config"

    fun load(context: Context): VeyraThemeConfig {
        val prefs = context.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val id = prefs.getString(KEY_THEME_ID, VeyraThemePresets.Glassmorphism.id)
        val preset = VeyraThemePresets.byId(id)

        // Custom field storage is added through saveCustom in the next editor milestone.
        return preset
    }

    fun savePreset(context: Context, config: VeyraThemeConfig) {
        context.applicationContext
            .getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_THEME_ID, config.id)
            .apply()
    }

    fun saveCustom(context: Context, config: VeyraThemeConfig) {
        context.applicationContext
            .getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_THEME_ID, config.id)
            .putString(KEY_CUSTOM_CONFIG, encode(config))
            .apply()
    }

    private fun encode(config: VeyraThemeConfig): String = listOf(
        config.displayName,
        config.backgroundTop,
        config.backgroundBottom,
        config.surfaceGlass,
        config.surfaceGlassStrong,
        config.glassBorder,
        config.primary,
        config.secondary,
        config.textPrimary,
        config.textSecondary,
        config.glassOpacity.toString(),
        config.borderOpacity.toString()
    ).joinToString("|")

    private fun decode(id: String, encoded: String): VeyraThemeConfig? {
        val fields = encoded.split("|")
        if (fields.size != 12) return null
        return runCatching {
            VeyraThemeConfig(
                id = id,
                displayName = fields[0],
                backgroundTop = fields[1],
                backgroundBottom = fields[2],
                surfaceGlass = fields[3],
                surfaceGlassStrong = fields[4],
                glassBorder = fields[5],
                primary = fields[6],
                secondary = fields[7],
                textPrimary = fields[8],
                textSecondary = fields[9],
                glassOpacity = fields[10].toFloat().coerceIn(0f, 1f),
                borderOpacity = fields[11].toFloat().coerceIn(0f, 1f)
            )
        }.getOrNull()
    }
}
