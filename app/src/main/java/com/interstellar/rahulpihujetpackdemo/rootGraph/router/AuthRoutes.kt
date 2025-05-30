package com.interstellar.rahulpihujetpackdemo.rootGraph.router

// Auth Graph Routes
sealed class AuthRoutes(val route: String) {
    data object Welcome : AuthRoutes("welcomeFragment")
    data object Login : AuthRoutes("loginFragment")
    data object Register : AuthRoutes("registerFragment")
}
