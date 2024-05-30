package com.example.userinfocollector.Presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.userinfocollector.Presentation.ViewModel.UserViewModel
import com.example.userinfocollector.Presentation.theme.UserInfoCollectorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UserInfoCollectorTheme {
                val navController: NavController = rememberNavController()
                 val userViewModel =  UserViewModel(application)
                NavGroupSetUp(
                    navController as NavHostController,
                    userViewModel,
                )
            }
        }
    }
}

@Composable
fun NavGroupSetUp(
    navController: NavHostController,
    viewModel: UserViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            UserFormScreen(viewModel, onItemClick = {
                navController.navigate(route = Screen.UserDetails.route)
            })
        }
        composable(
            route = Screen.UserDetails.route,
        ) {
            ShowAllUsersScreen(viewModel)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    UserInfoCollectorTheme {
        var viewModel = UserViewModel(applicationContext = LocalContext.current)
        UserFormScreen(viewModel, onItemClick = {})
    }
}