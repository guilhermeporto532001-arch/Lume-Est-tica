package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LumeColorScheme = lightColorScheme(
    primary = LumePrimarySage,
    onPrimary = Color.White,
    primaryContainer = LumeSageLight,
    onPrimaryContainer = LumeSageDark,
    secondary = LumeChampagneGold,
    onSecondary = Color.White,
    secondaryContainer = LumeChampagneSand,
    onSecondaryContainer = LumeChampagneText,
    tertiary = LumeTerracottaBlush,
    onTertiary = Color.White,
    tertiaryContainer = LumeRosePastel,
    onTertiaryContainer = LumeRoseDark,
    background = LumeCanvas,
    onBackground = LumeNeutralDark,
    surface = LumeSurfacePorcelain,
    onSurface = LumeNeutralDark,
    surfaceVariant = LumeSageContainer,
    onSurfaceVariant = LumeNeutralMuted,
    outline = LumeBorderSubtle,
    outlineVariant = Color(0xFFDCE2DF)
)

@Composable
fun LumeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LumeColorScheme,
        typography = LumeTypography,
        content = content
    )
}
