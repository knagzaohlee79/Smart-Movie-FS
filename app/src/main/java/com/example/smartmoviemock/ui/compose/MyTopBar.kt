package com.example.smartmoviemock.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmoviemock.R
import com.example.smartmoviemock.ui.compose.theme.MainTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    onBackButtonClick: () -> Unit = {}
) {
    MainTheme {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Movie Detail",
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = { onBackButtonClick() },
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Menu",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            },
            actions = {
                IconButton(onClick = { /* Handle navigation icon click */ }) {

                }
            },
            modifier = Modifier
                .height(50.dp),
            colors = TopAppBarColors(
                containerColor = colorResource(id = R.color.colorPrimaryDark),
                titleContentColor = colorResource(id = R.color.colorTextPrimary),
                actionIconContentColor = MaterialTheme.colorScheme.secondary,
                navigationIconContentColor = MaterialTheme.colorScheme.secondary,
                scrolledContainerColor = Color.White,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    MyTopBar()
}

