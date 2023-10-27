package com.example.pokedex.ui.pokedex_navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.data.local.model.Pokemon
import com.example.pokedex.ui.screen.pokemon_detail.PokemonDetailsScreen
import com.example.pokedex.ui.screen.pokemon_list.PokemonListScreen
import com.example.pokedex.util.PokeDexAppConstants.KEY_POKEMON

@Composable
fun PokedexAppNavigation() {
    val navController = rememberNavController()
    var selectedPokemon by remember {
        mutableStateOf<Pokemon?>(null)
    }
    NavHost(
        navController = navController,
        startDestination = PokedexScreens.PokemonListScreen.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(250)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(250)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(250)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(250)
            )
        }
    ) {
        composable(PokedexScreens.PokemonListScreen.route) {
            PokemonListScreen(
                navController
            )
        }
        composable(PokedexScreens.PokemonDetailsScreen.route) {
            selectedPokemon =
                navController.previousBackStackEntry?.savedStateHandle?.get(
                    KEY_POKEMON
                )
            PokemonDetailsScreen(selectedPokemon, navController)
        }
    }
}