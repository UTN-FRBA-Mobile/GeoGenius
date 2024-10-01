package com.utnfrba.geogenius.navbar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.utnfrba.geogenius.ui.theme.GeoGeniusTheme

@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf("Map") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomNavigationBarItem().getBottomNavigationItems().forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(if (selectedItem == item.label) item.filledIcon else item.outlinedIcon,
                                contentDescription = item.label)
                        },
                        selected = selectedItem == item.label,
                        onClick = {
                            selectedItem = item.label
//                            navController.navigate(item.route) {
//                                popUpTo(navController.graph.findStartDestination().id) {
//                                    saveState = true
//                                }
//                                launchSingleTop = true
//                                restoreState = true
//                            }
                          },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ){ paddingValues ->
//        NavHost(navController = navController, startDestination = "") {
//            composable<String> { Text("") }
//        }
//        https://medium.com/@bharadwaj.rns/bottom-navigation-in-jetpack-compose-using-material3-c153ccbf0593
    }

}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    GeoGeniusTheme(darkTheme = true) {
        BottomNavigationBar()
    }
}
