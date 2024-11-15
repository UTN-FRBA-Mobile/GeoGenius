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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.utnfrba.geogenius.appnavigation.Screen

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

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
