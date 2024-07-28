package com.example.smartmoviemock.ui.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartmoviemock.R
import com.example.smartmoviemock.ui.compose.theme.BlueMain
import com.example.smartmoviemock.ui.compose.theme.GrayText
import com.example.smartmoviemock.ui.compose.theme.MainTheme

@Composable
fun BottomNavigationBar(items: List<String>, selectedItem: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        NavigationBarItem(
            selected = selectedItem == 0,
            onClick = { onItemSelected(0) },
            icon = {
                Icon(
                    painterResource(id = R.drawable.ic_discover),
                    contentDescription = items[0],
                    tint = if (selectedItem == 0) BlueMain else GrayText
                )
            },
            label = {
                Text(
                    items[0],
                    color = if (selectedItem == 0) BlueMain else GrayText
                )
            }
        )
        NavigationBarItem(
            selected = selectedItem == 1,
            onClick = { onItemSelected(1) },
            icon = {
                Icon(
                    painterResource(id = R.drawable.ic_genres),
                    contentDescription = items[1],
                    tint = if (selectedItem == 1) BlueMain else GrayText
                )
            },
            label = {
                Text(
                    text = items[1],
                    color = if (selectedItem == 1) BlueMain else GrayText
                )
            }
        )
        NavigationBarItem(
            selected = selectedItem == 2,
            onClick = { onItemSelected(2) },
            icon = {
                Icon(
                    painterResource(id = R.drawable.ic_cast),
                    contentDescription = items[2],
                    tint = if (selectedItem == 2) BlueMain else GrayText
                )
            },
            label = {
                Text(
                    items[2],
                    color = if (selectedItem == 2) BlueMain else GrayText
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    MainTheme {
        BottomNavigationBar(
            items = listOf("Discover", "Genres", "Artists"),
            selectedItem = 0,
            onItemSelected = {

            }
        )
    }
}