package com.morrison.recipeapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.morrison.recipeapp.ui.RecipeAppTheme
import com.morrison.recipeapp.ui.screens.HomeScreenRoute
import com.morrison.recipeapp.ui.screens.LogInScreenRoute
import com.morrison.recipeapp.ui.screens.RegisteScreenRoute
import com.morrison.recipeapp.ui.screens.auth.LogInScreen
import com.morrison.recipeapp.ui.screens.auth.RegisterScreen
import com.morrison.recipeapp.ui.screens.homeScreen.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    RecipeAppTheme {
        val nav = rememberNavController()
        NavHost(
            navController = nav,
            startDestination = LogInScreenRoute
        ) {
            composable<RegisteScreenRoute> {
                RegisterScreen(nav)
            }

            composable<LogInScreenRoute> {
                LogInScreen(nav)
            }

            composable<HomeScreenRoute> {
                HomeScreen(nav)
            }
        }
    }
}