package com.sfj3861.veyra.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ThemeSettingsScreen(
    config: VeyraThemeConfig,
    onConfigChange: (VeyraThemeConfig) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(
                        colorFromHex(config.backgroundTop),
                        colorFromHex(config.backgroundBottom)
                    )
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Column(Modifier.weight(1f)) {
                Text("Personalización", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Adapta Veyra a tu estilo",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
            IconButton(onClick = { onConfigChange(VeyraThemePresets.Glassmorphism) }) {
                Icon(Icons.Default.Refresh, contentDescription = "Restablecer")
            }
        }

        Spacer(Modifier.height(24.dp))
        Text("TEMAS", fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
        Spacer(Modifier.height(10.dp))

        VeyraThemePresets.all.forEach { preset ->
            PresetRow(
                config = preset,
                selected = config.id == preset.id,
                onClick = { onConfigChange(preset) }
            )
            Spacer(Modifier.height(9.dp))
        }

        Spacer(Modifier.height(18.dp))
        Text("COLORES PERSONALIZADOS", fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
        Spacer(Modifier.height(5.dp))
        Text(
            "Introduce colores HEX. Ejemplo: #398BFF",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp
        )
        Spacer(Modifier.height(12.dp))

        HexField("Color principal", config.primary) {
            onConfigChange(config.copy(id = "custom", displayName = "Personalizado", primary = normalizeHex(it)))
        }
        HexField("Color secundario", config.secondary) {
            onConfigChange(config.copy(id = "custom", displayName = "Personalizado", secondary = normalizeHex(it)))
        }
        HexField("Fondo superior", config.backgroundTop) {
            onConfigChange(config.copy(id = "custom", displayName = "Personalizado", backgroundTop = normalizeHex(it)))
        }
        HexField("Fondo inferior", config.backgroundBottom) {
            onConfigChange(config.copy(id = "custom", displayName = "Personalizado", backgroundBottom = normalizeHex(it)))
        }

        Spacer(Modifier.height(16.dp))
        Text("Transparencia del cristal", fontWeight = FontWeight.SemiBold)
        Text(
            "${(config.glassOpacity * 100).toInt()} %",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp
        )
        Slider(
            value = config.glassOpacity,
            onValueChange = {
                onConfigChange(config.copy(id = "custom", displayName = "Personalizado", glassOpacity = it))
            },
            valueRange = 0.02f..0.90f
        )

        Spacer(Modifier.height(18.dp))
        Button(
            onClick = { onConfigChange(VeyraThemePresets.Glassmorphism) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Restaurar tema predeterminado")
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun PresetRow(
    config: VeyraThemeConfig,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = Color.White.copy(alpha = if (selected) 0.16f else 0.08f),
        border = BorderStroke(
            if (selected) 1.5.dp else 1.dp,
            if (selected) MaterialTheme.colorScheme.primary else Color.White.copy(alpha = 0.14f)
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                listOf(config.backgroundTop, config.primary, config.secondary).forEach { hex ->
                    Surface(
                        modifier = Modifier
                            .height(28.dp)
                            .weight(1f),
                        color = colorFromHex(hex),
                        shape = RoundedCornerShape(8.dp)
                    ) {}
                }
            }
            Column(Modifier.weight(2f).padding(start = 14.dp)) {
                Text(config.displayName, fontWeight = FontWeight.SemiBold)
                Text(
                    if (selected) "Activo" else "Aplicar tema",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun HexField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        singleLine = true,
        shape = RoundedCornerShape(14.dp)
    )
}

private fun normalizeHex(value: String): String {
    val cleaned = value.trim().uppercase().removePrefix("#")
    return if (cleaned.matches(Regex("[0-9A-F]{6}"))) "#$cleaned" else "#398BFF"
}
