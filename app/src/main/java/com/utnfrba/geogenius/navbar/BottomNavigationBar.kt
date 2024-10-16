package com.utnfrba.geogenius.navbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.utnfrba.geogenius.bookmarkscreen.samplePlace
import com.utnfrba.geogenius.screens.BookmarkScreen
import com.utnfrba.geogenius.screens.FilterScreen
import com.utnfrba.geogenius.screens.MapScreen
import com.utnfrba.geogenius.ui.theme.GeoGeniusTheme

@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(1) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomNavigationBarItem().getBottomNavigationItems().forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(if (selectedItem == index) item.filledIcon else item.outlinedIcon,
                                contentDescription = item.label)
                        },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item.route.toString()) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                          },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ){ paddingValues ->
        NavHost(navController,
            startDestination = Screen.Map.toString(),
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Filter.toString()){
                FilterScreen()
            }
            composable(Screen.Map.toString()){
                MapScreen(activity = LocalContext.current as FragmentActivity)
            }
            composable(Screen.Bookmark.toString()) {
                BookmarkScreen(listOf(samplePlace, samplePlace))
            }
        }
    }

}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    GeoGeniusTheme(darkTheme = true) {
        BottomNavigationBar()
    }
}
