package com.sfj3861.veyra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sfj3861.veyra.ui.home.BrowserHome
import com.sfj3861.veyra.ui.theme.VeyraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VeyraTheme {
                BrowserHome()
            }
        }
    }
}
