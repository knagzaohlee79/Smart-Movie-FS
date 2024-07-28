package com.example.smartmoviemock.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.ui.compose.theme.MainTheme
import com.example.smartmoviemock.utility.Constant
import com.example.smartmoviemock.utility.CommonFunction
import com.example.smartmoviemock.R

@Composable
fun MovieInfoSection(movie: Movie, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AsyncImage(
            model = Constant.IMAGE_BASE_URL + movie.posterUrl,
            placeholder = painterResource(id = R.drawable.movie_placeholder),
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .weight(1.35f)
                .height(200.dp)
                .padding(10.dp, 5.dp)
        )
        Column(
            modifier = modifier
                .weight(2f)
                .padding(5.dp, 5.dp)
                .height(190.dp)
        ) {
            Text(
                text = movie.title,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(3.dp, 3.dp)
            )

            Text(
                text = CommonFunction.getDisplayStringGenres(movie.movieGenres),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(3.dp, 2.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row {
                RatingBar(
                    rating = movie.rating / 2f,
                    onRatingChanged = {},
                    starPadding = 0,
                    starSize = 24,
                    readOnly = true,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Text(
                    text = String.format("%.1f/10", movie.rating),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(7.dp, 5.dp)
                        .align(alignment = Alignment.CenterVertically)
                )
            }

            Text(
                text = "Language: ${CommonFunction.getFullLanguageName(movie.originalLanguage)}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(3.dp, 2.dp)
            )

            Text(
                text = "${movie.releaseDate} (${movie.originalCountry}) ${
                    CommonFunction.convertMovieDuration(
                        movie.duration
                    )
                }",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(3.dp, 2.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieInfoSectionPreview() {
    MainTheme {
        val movie = Movie(
            id = 1,
            title = "Howl's Moving Castle",
            releaseDate = "2004",
            category = listOf(1, 2, 3),
            movieGenres = listOf("Anime", "Adventure", "Fantasy"),
            rating = 4.3f,
            posterUrl = "",
            description = stringResource(id = R.string.puss_in_boots_des),
            isFavorite = false
        )
        MovieInfoSection(movie = movie, Modifier)
    }
}