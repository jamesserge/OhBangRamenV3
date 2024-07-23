package com.boss.ohbangramen

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun Navigation(navController: NavHostController, sharedPreferences: SharedPreferences, menuViewModel: MenuViewModel) {
    val userRegistered = remember { sharedPreferences.getBoolean("user_registered", false)}
    val startDestination = if (userRegistered) "home" else "onboarding"

    NavHost(navController = navController, startDestination =  startDestination) {
        composable("home") { Home(navController, sharedPreferences, menuViewModel) }
        composable("profile") { Profile(navController, sharedPreferences) }
        composable("onboarding") { Onboarding(navController, sharedPreferences) }

        // Define the navigation route for FoodDetailScreen with id parameter
        composable(
            route = "foodDetail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("id") ?: -1
            FoodDetailScreen(navController = navController, viewModel = menuViewModel, itemId = itemId)
        }
    }
}