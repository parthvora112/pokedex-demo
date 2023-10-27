@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)

package com.example.pokedex.ui.screen.pokemon_list

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pokedex.R
import com.example.pokedex.data.local.model.Pokemon
import com.example.pokedex.ui.pokedex_navigation.PokedexScreens
import com.example.pokedex.ui.screen.components.PokedexActionBar
import com.example.pokedex.util.PokeDexAppConstants.KEY_POKEMON

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val pokemonListState by viewModel.pokemonListScreenState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        PokedexActionBar(
            isShowBack = false,
            "PokeDex",
            onBackPressed = {}
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ScreenBackGround()
            Column {
                pokemonListState.isLoading?.let { isLoading ->
                    ScreenLoader(isLoading)
                }
                pokemonListState.onError?.let {
                    ErrorView(it) {
                        viewModel.fetchPokemonList()
                    }
                }
                pokemonListState.pokemonList?.let { pokemonList ->
                    PokemonListView(pokemonList, navController)
                }
            }
        }
    }
}

@Composable
fun ScreenBackGround() {
    Image(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .padding(top = 22.dp)
            .rotate(30F)
            .alpha(0.1F),
        painter = painterResource(id = R.drawable.pokemon_ball),
        contentDescription = ""
    )
}

@Composable
fun PokemonListView(pokemonList: ArrayList<Pokemon>, navController: NavController) {
    val scrollState = rememberLazyListState()
    if (pokemonList.isNotEmpty()) {
        LazyColumn(
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.size(0.dp))
            }
            items(pokemonList) { invoice ->
                PokemonItem(
                    pokemon = invoice, navController = navController
                )
            }
            item {
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No data found..", fontSize = 20.sp)
        }
    }
}

@Composable
fun ErrorView(it: String, onRetryClick: () -> Unit) {
    val context = LocalContext.current
    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = onRetryClick) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun ScreenLoader(isLoading: Boolean) {
    AnimatedVisibility(
        visible = isLoading, enter = scaleIn(), exit = scaleOut()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

@Composable
fun PokemonItem(pokemon: Pokemon, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        onClick = {
            navController.currentBackStackEntry?.savedStateHandle?.apply {
                set(
                    KEY_POKEMON, pokemon
                )
            }
            navController.navigate(PokedexScreens.PokemonDetailsScreen.route)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            pokemon.details?.imageUrls?.let { pokemonImageUrls ->
                if (pokemonImageUrls.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(
                                    topEnd = 20.dp,
                                    bottomEnd = 20.dp,
                                    topStart = 0.dp,
                                    bottomStart = 0.dp
                                )
                            )
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(pokemonImageUrls.first())
                                    .apply(block = fun ImageRequest.Builder.() {
                                        placeholder(R.drawable.pokemon_ball)
                                    }).build()
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        )
                    }

                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(1F)
                    .padding(8.dp)
            ) {
                Text(
                    text = pokemon.name.capitalize(Locale.current),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.pokemon_type_title),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    pokemon.details?.pokemonTypes?.let { pokemonTypes ->
                        pokemonTypes.forEach {
                            SuggestionChip(
                                modifier = Modifier.height(25.dp),
                                onClick = {},
                                label = { Text(it.capitalize(Locale.current), fontSize = 14.sp) }
                            )
                        }
                    }
                }
            }
        }
    }
}
