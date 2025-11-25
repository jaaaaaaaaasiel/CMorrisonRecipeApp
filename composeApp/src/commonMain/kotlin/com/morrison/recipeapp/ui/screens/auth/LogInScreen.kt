package com.morrison.recipeapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.morrison.recipeapp.ui.RecipeAppTheme
import com.morrison.recipeapp.ui.components.LoadingOverlay
import com.morrison.recipeapp.ui.screens.HomeScreenRoute
import com.morrison.recipeapp.ui.screens.LogInScreenRoute
import com.morrison.recipeapp.ui.screens.auth.components.AuthCard
import com.morrison.recipeapp.ui.screens.auth.components.Background
import com.morrison.recipeapp.ui.viewmodels.AuthViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.reflect.KClass

@Composable
fun LogInScreen(
    nav: NavController
){
    val colors = MaterialTheme.colorScheme
    val viewModel: AuthViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return AuthViewModel() as T
            }
        }
    )

    LaunchedEffect(viewModel.isLogged){
        if (viewModel.isLogged){
            nav.navigate(HomeScreenRoute){
                popUpTo(LogInScreenRoute){ inclusive = true }
            }
        }
    }

    if (viewModel.isLoading) {
        LoadingOverlay(
            text = "Cargando...",
            icon = Icons.AutoMirrored.Filled.Login
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ){
        Background {

            AuthCard(
                nav = nav,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .height(350.dp)
                    .padding(horizontal = 24.dp)
                    .shadow(10.dp,RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(colors.surface)
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                title = "Log In",
                nameValue = "",
                onNameChange = {newValue -> newValue},
                emailValue = viewModel.email,
                onEmailChange = {newValue -> viewModel.email = newValue},
                passwordValue = viewModel.password,
                onPasswordChange = {newValue -> viewModel.password = newValue},
                confirmPasswordValue = "confirmPassword",
                onConfirmPasswordChange = {newValue -> newValue}
            ) {
                if (viewModel.email.isBlank() || viewModel.password.isBlank()) {
                    return@AuthCard
                }
                viewModel.login(
                    email = viewModel.email,
                    password = viewModel.password
                ) { success, message ->
                    if (success) viewModel.isLogged = true
                }

            }
        }
    }
}

@Preview
@Composable
fun LISPreview(){
    RecipeAppTheme {
        LogInScreen(rememberNavController())
    }
}