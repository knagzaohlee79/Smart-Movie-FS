package com.example.smartmoviemock.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.ui.compose.theme.MainTheme
import com.example.smartmoviemock.utility.CommonFunction

@Composable
fun MovieDescriptionSection(
    movie: Movie,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var localIsExpanded by remember { mutableStateOf(isExpanded) }
    var isLongText by remember { mutableStateOf(false) }

    // Synchronize the local state with the parent state
    LaunchedEffect(isExpanded) {
        localIsExpanded = isExpanded
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = movie.description,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
            maxLines = if (localIsExpanded) Int.MAX_VALUE else 3,
            overflow = if (localIsExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(10.dp, 5.dp, 10.dp, 5.dp)
        )

        Box(
            modifier = Modifier
                .padding(10.dp, 0.dp, 10.dp, 5.dp)
                .clickable {
                    localIsExpanded = !localIsExpanded
                    onExpandedChange(localIsExpanded)
                }
        ) {
            Text(
                text = if (localIsExpanded) "show less" else "view all",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDescriptionSectionPreview() {
    MainTheme {
        MovieDescriptionSection(
            movie = CommonFunction.emptyMovie,
            isExpanded = false,
            onExpandedChange = {})
    }
}



