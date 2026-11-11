package com.sfj3861.veyra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import com.sfj3861.veyra.ui.home.BrowserHome
import com.sfj3861.veyra.ui.theme.VeyraTheme
import com.sfj3861.veyra.ui.theme.VeyraThemeStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val savedTheme = remember { VeyraThemeStore.load(this@MainActivity) }
            VeyraTheme(config = savedTheme) {
                BrowserHome()
            }
        }
    }
}
