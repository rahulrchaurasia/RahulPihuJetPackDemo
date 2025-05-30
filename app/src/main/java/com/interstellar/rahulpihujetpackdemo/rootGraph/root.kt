package com.interstellar.rahulpihujetpackdemo.rootGraph

// 1. Navigation Routes Structure (Equivalent to XML)
object NavGraphs {
    const val ROOT = "nav_graph"
    const val AUTH = "auth_graph"
    const val HOME = "home_graph"
}

sealed class GlobalActions(val route: String) {
    data object ToHome : GlobalActions("action_global_to_home")
    data object ToLogin : GlobalActions("action_global_to_login")
    data object ToAuth : GlobalActions("action_global_to_auth")
}
