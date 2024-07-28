package com.example.smartmoviemock.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartmoviemock.data.model.Cast
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.ui.compose.theme.MainTheme
import com.example.smartmoviemock.ui.compose.theme.WhiteBackground
import com.example.smartmoviemock.viewmodel.MovieDetailViewModel
import com.example.smartmoviemock.R


@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    movie: Movie?,
    cast: List<Cast> = listOf(),
    similarMovies: List<Movie> = listOf()
) {
    val scrollState = rememberScrollState()
    val isExpanded by viewModel.isExpanded.collectAsState()

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .background(WhiteBackground)
        ) {
            if (movie != null) {
                Column {
                    MovieInfoSection(movie = movie)

                    MovieDescriptionSection(
                        movie = movie,
                        isExpanded = isExpanded,
                        onExpandedChange = { isExpanded ->
                            viewModel.setExpanded(isExpanded)
                        })

                    LaunchedEffect(scrollState.value) {
                        if (scrollState.value > 0) {
                            viewModel.setExpanded(false)
                        }
                    }

                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                    ) {
                        if (cast.isNotEmpty()) {
                            CastGridSectionView(
                                title = "Cast",
                                listCast = cast,
                                height = 400
                            )
                        }

                        if (similarMovies.isNotEmpty()) {
                            MovieGridSection(
                                title = "Similar Movies",
                                listMovies = similarMovies,
                                height = 420
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovieDetailScreenPreview() {
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

    val mockMovie = Movie(
        id = 1,
        title = "Howl's Moving Castle",
        releaseDate = "2004",
        category = listOf(1, 2, 3),
        movieGenres = listOf("Anime", "Adventure", "Fantasy"),
        rating = 8.6f,
        posterUrl = "",
        description = stringResource(id = R.string.puss_in_boots_des),
        isFavorite = false
    )
    val mockViewModel = hiltViewModel<MovieDetailViewModel>()
    MainTheme {
        MovieDetailScreen(
            viewModel = mockViewModel,
            movie = mockMovie,
            cast = listOf(mockCast, mockCast, mockCast, mockCast, mockCast, mockCast),
            similarMovies = listOf(mockMovie, mockMovie, mockMovie, mockMovie, mockMovie, mockMovie)
        )
    }
}

