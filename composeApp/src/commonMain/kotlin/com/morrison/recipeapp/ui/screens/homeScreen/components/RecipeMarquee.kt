package com.morrison.recipeapp.ui.screens.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.morrison.recipeapp.domain.models.Recipe

@Composable
fun RecipeMarquee(
    item: Recipe,
    onClick:  () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .background(colors.surface)
            .padding(8.dp)
            .clickable{
                onClick
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = item.title,
            modifier = Modifier
                .size(65.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(Modifier
            .width(200.dp)
        ) {
            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                color = colors.onSurface,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = item.category,
                color = colors.onSurfaceVariant,
                fontSize = 12.sp
            )
        }

        Icon(
            imageVector = Icons.Default.Schedule,
            contentDescription = null,
            modifier = Modifier
                .size(15.dp)
                .clip(CircleShape),
            tint = colors.primary
        )
        Text(
            text = "${item.minutes} min",
            color = colors.primary
        )
    }
}