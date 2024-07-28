package com.example.smartmoviemock.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartmoviemock.ui.compose.theme.StarColorFilled
import com.example.smartmoviemock.ui.compose.theme.StarColorOutlined
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
    rating: Float,
    onRatingChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
    stars: Int = 5,
    starSize: Int = 32,
    starPadding: Int = 4,
    filledStarColor: Color = StarColorFilled,
    outlinedStarColor: Color = StarColorOutlined,
    readOnly: Boolean = false
) {
    Row(modifier = modifier) {
        for (i in 1..stars) {
            val icon = when {
                i <= floor(rating).toInt() -> Icons.Filled.Star
                i == ceil(rating).toInt() -> Icons.Filled.StarHalf
                else -> Icons.Outlined.StarBorder
            }
            val tint =
                if (i <= rating || i == ceil(rating).toInt()) filledStarColor else outlinedStarColor
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier
                    .padding(horizontal = starPadding.dp)
                    .let {
                        if (!readOnly) {
                            it.clickable { onRatingChanged(i.toFloat()) }
                        } else {
                            it
                        }
                    }
                    .size(starSize.dp)
            )
        }
    }
}