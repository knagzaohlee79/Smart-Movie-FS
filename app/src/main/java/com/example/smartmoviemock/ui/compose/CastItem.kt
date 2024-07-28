package com.example.smartmoviemock.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.smartmoviemock.data.model.Cast
import com.example.smartmoviemock.ui.compose.theme.GrayBackground
import com.example.smartmoviemock.ui.compose.theme.GrayText
import com.example.smartmoviemock.ui.compose.theme.WhiteBackground
import com.example.smartmoviemock.utility.Constant
import com.example.smartmoviemock.R

@Composable
fun CastItem(
    cast: Cast
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, GrayBackground, RoundedCornerShape(8.dp))
            .background(WhiteBackground)
            .height(200.dp)
            .width(120.dp)
    ) {
        AsyncImage(
            model = Constant.IMAGE_BASE_URL + cast.profilePath,
            placeholder = painterResource(id = R.drawable.cast_imageholder),
            error = painterResource(id = R.drawable.cast_imageholder),
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(5f)
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(2.3f)
        ) {
            Text(
                text = cast.name,
                fontSize = 16.sp,
                textAlign = TextAlign.Center, // Center align the text
                color = GrayText,
                modifier = Modifier
                    .padding(0.dp, 5.dp)
                    .height(50.dp)
                    .width(70.dp)
                ,
                softWrap = true // Enable soft wrapping
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActorItemsPreview() {
    val mockCast = Cast(
        adult = false,
        gender = 1,
        id = 56322,
        knownForDepartment = "Acting",
        name = "Amy Poehler",
        originalName = "Amy Poehler",
        popularity = 20.163,
        profilePath = "/rwmvRonpluV6dCPiQissYrchvSD.jpg",
        castId = 4,
        character = "Joy (voice)",
        creditId = "631bd7450bb076007b78d023",
        order = 0
    )
    CastItem(cast = mockCast)
}