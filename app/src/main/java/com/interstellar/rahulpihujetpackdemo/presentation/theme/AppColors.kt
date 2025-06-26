package com.interstellar.rahulpihujetpackdemo.presentation.theme



import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme

object AppColors {
    val cardBackground: Color
        @Composable
        get() = if (isSystemInDarkTheme()) DarkCardBackground else LightCardBackground

    val cardGray: Color
        @Composable get() = if (isSystemInDarkTheme()) DarkCardGray else LightCardGray

    val cardBorder: Color
        @Composable get() = if (isSystemInDarkTheme()) Color(0xFF444444) else Color(0xFFDDDDDD)


    val cardBorderBlue: Color
        @Composable get() = if (isSystemInDarkTheme()) DarkCardBorderBlue else LightCardBorderBlue


    val primary: Color
        @Composable
        get() = if (isSystemInDarkTheme()) DarkPrimary else LightPrimary

    val onPrimary: Color
        @Composable
        get() = if (isSystemInDarkTheme()) DarkOnPrimary else LightOnPrimary

    val redBackground: Color
        @Composable get() = if (isSystemInDarkTheme()) DarkRedBackground else LightRedBackground

}
