package com.sfj3861.veyra.browser

data class BrowserTab(
    val id: String,
    val title: String = "Nueva pestaña",
    val url: String = "",
    val isLoading: Boolean = false,
    val progress: Int = 0,
    val canGoBack: Boolean = false,
    val canGoForward: Boolean = false,
    val isSelected: Boolean = false
)
