package com.abdulaziz.currencyconversion.android.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.abdulaziz.currencyconversion.theme.purple500


object AppStyles {

    val textTitle = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight(700))
    val textSubtitle = TextStyle(color = Color.fromHex(purple500), fontSize = 20.sp, fontWeight = FontWeight(500))
    val textBody = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight(500))
    val textBodySemiBold = TextStyle(color = Color.White, fontSize = 12.sp, fontWeight = FontWeight(500))
    val textHint = TextStyle(color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight(300))
    val textLabel = TextStyle(color = Color.DarkGray, fontSize = 12.sp, fontWeight = FontWeight(500))
    val textCaption = TextStyle(color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight(300))
}

fun Color.Companion.fromHex(color: String): Color  = Color(android.graphics.Color.parseColor(color))

