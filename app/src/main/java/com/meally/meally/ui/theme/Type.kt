package com.meally.meally.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.meally.meally.R

object Typography {
    val h1 =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            lineHeight = 36.sp,
        )

    val h2 =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 26.sp,
            lineHeight = 31.2.sp,
        )

    val h3 =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 24.sp,
        )

    val body1 =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 23.4.sp,
        )

    val body2 =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 20.8.sp,
        )

    val button =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 19.2.sp,
        )

    val numbers1 =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 44.sp,
            lineHeight = 52.8.sp,
        )

    val numbers2 =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 34.sp,
            lineHeight = 40.8.sp,
        )

    val materialTypography =
        Typography(
            headlineLarge = h1,
            headlineMedium = h2,
            headlineSmall = h3,
            bodyLarge = body1,
            bodyMedium = body2,
            bodySmall = body2,
        )
}

val PoppinsFamily =
    FontFamily(
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_semibold, FontWeight.SemiBold),
    )
