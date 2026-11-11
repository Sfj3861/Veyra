package com.sfj3861.veyra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sfj3861.veyra.ui.home.BrowserHome
import com.sfj3861.veyra.ui.theme.ThemeSettingsScreen
import com.sfj3861.veyra.ui.theme.VeyraTheme
import com.sfj3861.veyra.ui.theme.VeyraThemeConfig
import com.sfj3861.veyra.ui.theme.VeyraThemePresets
import com.sfj3861.veyra.ui.theme.VeyraThemeStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var themeConfig by remember {
                mutableStateOf(VeyraThemeStore.load(this@MainActivity))
            }
            var showThemeSettings by remember { mutableStateOf(false) }

            fun applyTheme(config: VeyraThemeConfig) {
                themeConfig = config
                if (config.id == "custom") {
                    VeyraThemeStore.saveCustom(this@MainActivity, config)
                } else {
                    VeyraThemeStore.savePreset(this@MainActivity, config)
                }
            }

            VeyraTheme(config = themeConfig) {
                if (showThemeSettings) {
                    ThemeSettingsScreen(
                        config = themeConfig,
                        onConfigChange = ::applyTheme,
                        onBack = { showThemeSettings = false }
                    )
                } else {
                    BrowserHome(onOpenThemeSettings = { showThemeSettings = true })
                }
            }
        }
    }
}
