package com.morrison.recipeapp.ui.screens.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Header(onLogOut: () -> Unit){
    val colors = MaterialTheme.colorScheme
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier
            .weight(1f)
        ) {
            Text(
                text= "Hola",
                fontWeight = FontWeight.Light
            )
            Text(
                text="Carlos Morrison",
                fontWeight = FontWeight.Bold
            )
        }

        Box(modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(colors.primary.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ){
            Text("CM")
        }

        Spacer(Modifier.width(10.dp))

        IconButton(
            onClick = onLogOut
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                null,
                tint = colors.primary
            )
        }

    }
}