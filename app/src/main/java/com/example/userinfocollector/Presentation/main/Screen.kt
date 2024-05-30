package com.example.userinfocollector.Presentation.main

sealed class Screen(val route: String){
    object Home : Screen(route = "Home")
    object UserDetails : Screen(route = "userDetails")
}