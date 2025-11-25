package com.morrison.recipeapp.ui.screens.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Tag(text: String,
        mod: Modifier,
        isBold: Boolean = false,
        onClick: () -> Unit
){
    val colors = MaterialTheme.colorScheme
    Text(
        text = text,
        modifier = mod
            .clip(CircleShape)
            .background(colors.primary.copy(alpha = 0.1f))
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .clickable{ onClick },
        color = colors.primary,
        fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
    )
}