package com.utnfrba.geogenius.navbar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun BottomNavigationBar(onClick: (route: String) -> Unit) {
    var selectedItem by remember { mutableIntStateOf(1) }

    NavigationBar {
        BottomNavigationBarItem().getBottomNavigationItems().forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == index) item.filledIcon else item.outlinedIcon,
                        contentDescription = item.label
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    onClick(item.route)
                },
                label = { Text(item.label) }
            )
        }
    }

}
