package com.morrison.recipeapp.ui.screens.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import com.morrison.recipeapp.ui.screens.LogInScreenRoute
import com.morrison.recipeapp.ui.screens.RegisteScreenRoute

@Composable
fun AuthCard(
    nav: NavController,
    modifier: Modifier,
    title: String,
    nameValue: String,
    onNameChange: (String) -> Unit,
    emailValue: String,
    onEmailChange: (String) -> Unit,
    passwordValue: String,
    onPasswordChange: (String) -> Unit,
    confirmPasswordValue: String,
    onConfirmPasswordChange: (String) -> Unit,
    onClick: () -> Unit
){
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title
        )
        if (title == "Create Account") {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = nameValue,
                shape = CircleShape,
                onValueChange = onNameChange ,
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Name"
                    )
                }
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = emailValue,
            shape = CircleShape,
            onValueChange = onEmailChange ,
            singleLine = true,
            placeholder = {
                Text(
                    text = "Mail"
                )
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = passwordValue,
            shape = CircleShape,
            onValueChange = onPasswordChange,
            singleLine = true,
            placeholder = {
                Text(
                    text = "Password"
                )
            },
            visualTransformation = PasswordVisualTransformation()
        )
        if (title == "Create Account") {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = confirmPasswordValue,
                shape = CircleShape,
                onValueChange = onConfirmPasswordChange ,
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Confirm Password"
                    )
                },
                visualTransformation = PasswordVisualTransformation()
            )
        }
        Button(
            onClick,
            modifier = Modifier.fillMaxWidth()
        ){
            if (title == "Create Account"){
                Text(
                    text = "Sign In",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            } else {
                Text(
                    text = "Log In",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        if (title == "Log In"){
            Text(
                modifier = Modifier
                    .clickable(
                        enabled = true, onClick =  {
                            nav.navigate(RegisteScreenRoute){
                                popUpTo(LogInScreenRoute){
                                    inclusive = true
                                }
                            }
                        }
                    )
                ,
                text = "¿No tienes una cuenta? Crea una",
                color = colors.primary,
                fontWeight = FontWeight.Bold,
            )
        }

    }
}