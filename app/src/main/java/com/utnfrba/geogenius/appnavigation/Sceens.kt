package com.utnfrba.geogenius.appnavigation

sealed class Screen(val route: String) {
    data object Filter : Screen("filter_route")
    data object Map : Screen("map_route")
    data object Bookmark : Screen("bookmark_route")
    data object BookmarkDetail : Screen("bookmark_detail")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg -> append("/$arg") }
        }
    }
}