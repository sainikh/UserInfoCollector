package com.example.userinfocollector.Presentation.ui

sealed class Screen(val route: String){
    object Home : Screen(route = "Home")
    object UserDetails : Screen(route = "userDetails")
}