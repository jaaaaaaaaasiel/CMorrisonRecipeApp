package com.morrison.recipeapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morrison.recipeapp.data.KtorfitClient
import com.morrison.recipeapp.domain.dtos.Login
import com.morrison.recipeapp.domain.dtos.Register
import com.morrison.recipeapp.domain.utils.Preferences
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    var email by mutableStateOf("")
    var  password by  mutableStateOf("")
    var isLogged by mutableStateOf(Preferences.getIsLogged())
    var name by  mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun register(
        name:String,
        email:String,
        password:String,
        onResult: (Boolean,String) -> Unit
    ){
        viewModelScope.launch {
            try{
                isLoading = true
                val service = KtorfitClient.createAuthService()
                val register = Register(
                    name = name,
                    email = email,
                    password = password
                )
                val result = service.register(register)
                if(result.isLogged){
                    // QUE EL USUARIO SE REGISTRO Y SE LOGUEO
                    println("Logueao")
                    Preferences.saveUserId(result.userId)
                    Preferences.saveIsLogged(result.isLogged)
                    onResult(true,result.message)
                    println(result.toString())
                }
                else{
                    // OCURRIO UN ERROR
                    onResult(false,result.message)
                    println("No logueao")
                    println(result.toString())
                }
            }
            catch (e: Exception){
                onResult(false,e.toString())
                print(e.toString())
            } finally {
                isLoading = false
            }
        }
    }

    fun login(
        email : String,
        password : String,
        onResult : (Boolean,String) -> Unit
    ){
        viewModelScope.launch {
            try{
                isLoading = true
                val service = KtorfitClient.createAuthService()
                val login = Login(
                    email = email,
                    password = password
                )
                val result = service.login(login)
                if(result.isLogged){
                    onResult(true,result.message)
                    Preferences.saveUserId(result.userId)
                    Preferences.saveIsLogged(result.isLogged)
                }
                else{
                    onResult(false,result.message)
                }
            }
            catch (e: Exception){
                onResult(false,"Error al loguearse")
                println(e.toString())
            } finally {
                isLoading = false
            }
        }
    }
}