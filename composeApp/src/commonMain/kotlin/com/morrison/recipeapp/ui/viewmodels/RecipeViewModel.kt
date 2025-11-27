package com.morrison.recipeapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morrison.recipeapp.data.KtorfitClient
import com.morrison.recipeapp.domain.dtos.Prompt
import com.morrison.recipeapp.domain.dtos.RecipeDTO
import com.morrison.recipeapp.domain.models.Recipe
import com.morrison.recipeapp.domain.utils.Preferences
import kotlinx.coroutines.launch

class RecipeViewModel: ViewModel() {
    val userId = Preferences.getUserId()

    val recipeService = KtorfitClient.createRecipeService()
    var recipes by mutableStateOf<List<Recipe>>(listOf())
    var fullRecipes by mutableStateOf<List<Recipe>>(listOf())
    var generatedRecipe by mutableStateOf<RecipeDTO?>(null)
    var showSheet by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var isSaved by mutableStateOf(false)


    init {
        getRecipes()
    }

    fun showModalFromList(recipe: RecipeDTO) {
        generatedRecipe = recipe
        showSheet = true
    }
    fun hideModal() {
        showSheet = false
    }
    fun generateRecipe(prompt: Prompt){
        viewModelScope.launch {
            try {
                isLoading = true
                val result = recipeService.generateRecipe(prompt)
                showSheet = true
                generatedRecipe = result
                println(result.toString())
            } catch (e: Error){
                showSheet = false
                println("Error de a debis:$")
                println(e.toString())
            } finally {
                isLoading = false
            }

        }
    }

    fun getRecipes(){
        viewModelScope.launch {
            try {
                val result = recipeService.getRecipesByUserId(userId)
                recipes  = result.takeLast(5).reversed()
                fullRecipes = result
                println(result.toString())
            } catch (e: Error){
                println(e.toString())
            }
        }
    }

    fun saveRecipe(){
        viewModelScope.launch {
            try {
                val recipe = Recipe(
                    id = 0,
                    userId = userId,
                    category = generatedRecipe?.category ?: "",
                    imageUrl = generatedRecipe?.imageUrl ?: "",
                    ingredients = generatedRecipe?.ingredients ?: listOf(),
                    instructions = generatedRecipe?.instructions ?: listOf(),
                    minutes = generatedRecipe?.stars ?: 0,
                    stars = generatedRecipe?.stars ?: 0,
                    title = generatedRecipe?.title ?: ""
                )
                val result = recipeService.saveGeneratedRecipe(recipe = recipe)
                isSaved = true
                getRecipes()
                println(result.toString())
            }catch (e: Exception){
                println(e.toString())
            }
        }
    }
}