package com.sfj3861.veyra.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tab
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sfj3861.veyra.ui.theme.VeyraColors

@Composable
fun BrowserHome(onOpenThemeSettings: () -> Unit = {}) {
    var address by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        VeyraColors.BackgroundTop,
                        Color(0xFF17243C),
                        VeyraColors.BackgroundBottom
                    )
                )
            )
    ) {
        // Decorative ambient lights. These are background accents, not controls.
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 90.dp, end = 8.dp)
                .size(190.dp)
                .blur(65.dp)
                .background(VeyraColors.Blue.copy(alpha = 0.22f), CircleShape)
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 4.dp, top = 100.dp)
                .size(170.dp)
                .blur(65.dp)
                .background(VeyraColors.Violet.copy(alpha = 0.18f), CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(14.dp))

            Header()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(48.dp))

                Text(
                    text = "VE",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                listOf(VeyraColors.Blue, VeyraColors.Violet)
                            ),
                            RoundedCornerShape(18.dp)
                        )
                        .padding(horizontal = 17.dp, vertical = 12.dp)
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Explora sin límites.",
                    color = VeyraColors.TextPrimary,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    text = "Tu espacio. Tus reglas.",
                    color = VeyraColors.TextSecondary,
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(28.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text(
                            "Buscar o escribir una dirección",
                            color = VeyraColors.TextSecondary
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = VeyraColors.TextSecondary
                        )
                    },
                    shape = RoundedCornerShape(22.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = VeyraColors.TextPrimary,
                        unfocusedTextColor = VeyraColors.TextPrimary,
                        focusedContainerColor = VeyraColors.SurfaceGlassStrong,
                        unfocusedContainerColor = VeyraColors.SurfaceGlass,
                        focusedBorderColor = VeyraColors.Blue.copy(alpha = 0.8f),
                        unfocusedBorderColor = VeyraColors.GlassBorder,
                        cursorColor = VeyraColors.Blue
                    )
                )

                Spacer(Modifier.height(30.dp))

                SectionHeading("Accesos rápidos", "Personalizables")
                Spacer(Modifier.height(13.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ShortcutCard("Favoritos", Icons.Default.BookmarkBorder, Modifier.weight(1f))
                    ShortcutCard("Pestañas", Icons.Default.Tab, Modifier.weight(1f))
                    ShortcutCard("Inicio", Icons.Default.Home, Modifier.weight(1f))
                }

                Spacer(Modifier.height(26.dp))

                GlassPanel {
                    Column(Modifier.padding(17.dp)) {
                        Text(
                            "Tu navegador, a tu manera",
                            color = VeyraColors.TextPrimary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Aquí podrás combinar temas, fondos, iconos y componentes creados por la comunidad.",
                            color = VeyraColors.TextSecondary,
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        )
                    }
                }

                Spacer(Modifier.height(22.dp))
            }

            BottomNavigation(onSettingsClick = onOpenThemeSettings)
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                "VEYRA",
                color = VeyraColors.TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )
            Text(
                "ANDROID • PREVIEW",
                color = VeyraColors.TextSecondary,
                fontSize = 9.sp,
                letterSpacing = 1.5.sp
            )
        }
        IconButton(onClick = { /* Profile screen will be added later. */ }) {
            Icon(
                Icons.Default.PersonOutline,
                contentDescription = "Perfil",
                tint = VeyraColors.TextPrimary
            )
        }
        IconButton(onClick = { /* Tab manager will be added later. */ }) {
            Icon(
                Icons.Default.Tab,
                contentDescription = "Pestañas",
                tint = VeyraColors.TextPrimary
            )
        }
    }
}

@Composable
private fun SectionHeading(title: String, detail: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            color = VeyraColors.TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        Text(detail, color = VeyraColors.Blue, fontSize = 11.sp)
    }
}

@Composable
private fun ShortcutCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    GlassPanel(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 7.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = VeyraColors.Blue)
            Spacer(Modifier.height(8.dp))
            Text(
                title,
                color = VeyraColors.TextPrimary,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun GlassPanel(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        color = VeyraColors.SurfaceGlass,
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(1.dp, VeyraColors.GlassBorder),
        content = content
    )
}

@Composable
private fun BottomNavigation(onSettingsClick: () -> Unit) {
    GlassPanel(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp, vertical = 7.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomItem(Icons.Default.Home, "Inicio", selected = true)
            BottomItem(Icons.Default.BookmarkBorder, "Guardados")
            BottomItem(Icons.Default.Tab, "Pestañas")
            BottomItem(Icons.Default.Settings, "Ajustes", onClick = onSettingsClick)
        }
    }
}

@Composable
private fun BottomItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 3.dp)
            .clickable(onClick = onClick)
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = if (selected) VeyraColors.Blue else VeyraColors.TextSecondary,
            modifier = Modifier.size(21.dp)
        )
        Spacer(Modifier.height(3.dp))
        Text(
            label,
            color = if (selected) VeyraColors.TextPrimary else VeyraColors.TextSecondary,
            fontSize = 9.sp
        )
    }
}
