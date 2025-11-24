package com.morrison.recipeapp.ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.morrison.recipeapp.domain.dtos.Prompt
import com.morrison.recipeapp.domain.dtos.RecipeDTO
import com.morrison.recipeapp.domain.utils.HideKeyboard
import com.morrison.recipeapp.domain.utils.Preferences
import com.morrison.recipeapp.ui.RecipeAppTheme
import com.morrison.recipeapp.ui.components.CustomTextField
import com.morrison.recipeapp.ui.components.LoadingOverlay
import com.morrison.recipeapp.ui.screens.HomeScreenRoute
import com.morrison.recipeapp.ui.screens.LogInScreenRoute
import com.morrison.recipeapp.ui.screens.homeScreen.components.GeneratedRecipe
import com.morrison.recipeapp.ui.screens.homeScreen.components.Header
import com.morrison.recipeapp.ui.screens.homeScreen.components.RecipeCard
import com.morrison.recipeapp.ui.screens.homeScreen.components.RecipeMarquee
import com.morrison.recipeapp.ui.screens.homeScreen.components.Tag
import com.morrison.recipeapp.ui.viewmodels.RecipeViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(nav: NavController){
    val colors = MaterialTheme.colorScheme
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val scope = rememberCoroutineScope()
    var prompt by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val viewModel: RecipeViewModel = viewModel()

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(colors.background)
        .safeContentPadding()
    ){
        //Header
        item {
             Header{
                 Preferences.clearSettings()
                 nav.navigate(LogInScreenRoute){
                    popUpTo(HomeScreenRoute) { inclusive = true }
                 }
             }
        }

        // Text Field
        item{
            Text(
                text= "Crea, cocina, comparte y disfruta",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier
                    .padding(top = 10.dp)
            )

            Spacer(Modifier.height(10.dp))

            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = prompt,
                onValueChange = { prompt = it},
                icon = Icons.Default.AutoAwesome,
                placeholder = "Escribe tus ingredientes aquí...",
                onTrailingIconClick = {
                    HideKeyboard(focusManager = focusManager)
                    viewModel.generateRecipe(Prompt(ingredients = prompt))
                    scope.launch {
                        sheetState.partialExpand()
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        HideKeyboard(focusManager = focusManager)
                        viewModel.generateRecipe(Prompt(ingredients = prompt))
                        scope.launch {
                            sheetState.partialExpand()
                        }
                    }
                )
            )
        }

        // Last Recipes
        item{
            Spacer(Modifier.height(10.dp))

            Text(
                text = "Your last recipes",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(Modifier.height(10.dp))

            LazyRow(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(viewModel.recipes){ recipe ->

                    RecipeCard(recipe){
                        scope.launch {
                            val recipeDTO = RecipeDTO(
                                category = recipe.category,
                                ingredients = recipe.ingredients,
                                instructions = recipe.instructions,
                                minutes = recipe.minutes,
                                stars = recipe.stars,
                                title = recipe.title,
                                imageUrl = recipe.imageUrl ?: "",
                                prompt = ""

                            )
                            viewModel.showModalFromList(recipeDTO)
                            sheetState.partialExpand()
                        }
                    }
                }
            }
        }

        // Generate Random Recipe
        item {
            Spacer(Modifier.height(10.dp))
            val tags = listOf("Quick (10 min)", "Low Calories", "Breakfast", "Ovenless")
            Text(text = "Quick Ideas... ",
                modifier = Modifier.padding(bottom = 5.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(tags){ tag ->
                    Tag(tag, true)
                }
            }

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.primary.copy(alpha = 0.1f))
                    .padding(all = 20.dp)
                    .clickable{
                        //Generar receta aleatoria
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Column {
                    Text(
                        text = "Not feeling inspired?",
                        fontWeight = FontWeight.Bold
                    )
                    Text("Generate a random recipe")
                }
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null
                )
            }

        }

        // All recipes
        item {
            Text(
                text = "All your recipes",
                modifier = Modifier.padding(vertical = 15.dp),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        items(viewModel.fullRecipes) { recipe ->
            RecipeMarquee(item = recipe){

            }
        }

    }

    if (viewModel.showSheet){
        ModalBottomSheet(
            onDismissRequest = { viewModel.hideModal() },
            dragHandle = { BottomSheetDefaults.DragHandle() },
            containerColor = colors.surface,
            sheetState = sheetState,
        ){

            GeneratedRecipe(recipe = viewModel.generatedRecipe)
        }
    }

    if (viewModel.isLoading){
        LoadingOverlay("Let me cook!")
    }
}

@Preview
@Composable
fun HSPreview(){
    RecipeAppTheme {
        HomeScreen(rememberNavController())
    }
}