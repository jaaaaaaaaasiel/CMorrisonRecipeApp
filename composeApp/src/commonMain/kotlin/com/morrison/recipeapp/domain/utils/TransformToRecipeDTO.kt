package com.morrison.recipeapp.domain.utils

import com.morrison.recipeapp.domain.dtos.RecipeDTO
import com.morrison.recipeapp.domain.models.Recipe

fun toRecipeDTO(recipe: Recipe) : RecipeDTO {
    return RecipeDTO(
        recipe.category,
        recipe.ingredients,
        recipe.instructions,
        recipe.minutes,
        "",
        recipe.stars,
        recipe.title,
        recipe.imageUrl
    )
}