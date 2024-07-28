package com.example.smartmoviemock.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.ui.compose.theme.GrayBackground
import com.example.smartmoviemock.ui.compose.theme.GrayText
import com.example.smartmoviemock.ui.compose.theme.LightGrayText
import com.example.smartmoviemock.ui.compose.theme.WhiteBackground
import com.example.smartmoviemock.utility.Constant
import com.example.smartmoviemock.utility.CommonFunction
import com.example.smartmoviemock.R

@Composable
fun SimilarMoviesItem(
    movie: Movie
) {
    Column(
        modifier = Modifier
            .height(180.dp)
            .width(380.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, GrayBackground, RoundedCornerShape(8.dp))
            .background(WhiteBackground)
    ) {
        AsyncImage(
            model = Constant.IMAGE_BASE_URL + movie.posterUrl,
            placeholder = painterResource(id = R.drawable.movie_placeholder),
            error = painterResource(id = R.drawable.movie_placeholder),
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(3f)
                .height(200.dp)
        )
        Row(
            modifier = Modifier
                .weight(1.5f)
                .padding(5.dp, 5.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(4f)
                    .padding(10.dp, 5.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = movie.title,
                    color = GrayText,
                    fontSize = 16.sp
                )
                Text(
                    text = CommonFunction.getDisplayStringGenres(movie.movieGenres),
                    color = LightGrayText,
                    fontSize = 14.sp
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(2f)
            ) {
                RatingBar(
                    rating = movie.rating / 2f,
                    onRatingChanged = {},
                    starSize = 22,
                    starPadding = 0,
                    readOnly = true
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimilarMoviesItemPreview() {
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
    SimilarMoviesItem(movie)
}