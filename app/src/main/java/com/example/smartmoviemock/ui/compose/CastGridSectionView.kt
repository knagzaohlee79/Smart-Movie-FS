package com.example.smartmoviemock.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmoviemock.data.model.Cast
import com.example.smartmoviemock.ui.compose.theme.BlueMain
import com.example.smartmoviemock.ui.compose.theme.MainTheme

@Composable
fun CastGridSectionView(
    modifier: Modifier = Modifier,
    height: Int = 350,
    title: String,
    listCast: List<Cast> = emptyList(),
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = title,
                color = BlueMain,
                fontSize = 18.sp,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(0.dp, 5.dp, 0.dp, 10.dp)
            )
            HorizontalDivider(
                color = BlueMain,
                thickness = 2.dp,
                modifier = Modifier.width(140.dp)
            )
        }

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onBackground)
        ) {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height.dp)
                    .padding(5.dp, 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(listCast) { cast ->
                    CastItem(cast = cast)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CastGridSectionPreview() {
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

    val listCast = listOf(mockCast, mockCast, mockCast, mockCast, mockCast, mockCast)

    MainTheme {
        CastGridSectionView(
            title = "Similar Movies",
            height = 450,
            listCast = listCast
        )
    }
}
