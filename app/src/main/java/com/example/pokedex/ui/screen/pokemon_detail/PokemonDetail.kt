@file:OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class
)

package com.example.pokedex.ui.screen.pokemon_detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokedex.R
import com.example.pokedex.data.local.model.Pokemon
import com.example.pokedex.ui.screen.components.PokedexActionBar
import com.example.pokedex.util.startOffsetForPage

@Composable
fun PokemonDetailsScreen(selectedPokemon: Pokemon?, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        PokedexActionBar(
            isShowBack = true,
            selectedPokemon?.name?.capitalize(Locale.current) ?: "PokeDex",
            onBackPressed = {
                navController.popBackStack()
            }
        )
        selectedPokemon?.details?.imageUrls?.let { pokemonImageUrls ->
            val pagerState = rememberPagerState(pageCount = {
                pokemonImageUrls.size
            })
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = 40.dp),
                ) { page ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 20.dp)
                            .graphicsLayer {
                                val startOffset = pagerState.startOffsetForPage(page)
                                translationX = size.width * (startOffset * .99f)

                                alpha = (2f - startOffset) / 2f
                                val scale = 1f - (startOffset * .1f)
                                scaleX = scale
                                scaleY = scale
                            }
                    ) {
                        if (pokemonImageUrls.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        ImageRequest.Builder(LocalContext.current)
                                            .decoderFactory(SvgDecoder.Factory())
                                            .data(pokemonImageUrls[page])
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
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(1F)
                .padding(horizontal = 20.dp)
        ) {
            item {
                Text(
                    text = selectedPokemon?.name?.capitalize(Locale.current) ?: "",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold)
                        ) {
                            append("Height : ")
                        }
                        append(selectedPokemon?.details?.height?.toString() ?: "-")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold)
                        ) {
                            append("Weight : ")
                        }
                        append(selectedPokemon?.details?.weight?.toString() ?: "-")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                PokemonExtraDetails(
                    stringResource(id = R.string.pokemon_type_title),
                    selectedPokemon?.details?.pokemonTypes
                )
                PokemonExtraDetails(
                    stringResource(id = R.string.pokemon_ability_title),
                    selectedPokemon?.details?.abilities
                )
                PokemonExtraDetails(
                    stringResource(id = R.string.pokemon_stats_title),
                    selectedPokemon?.details?.stat
                )
            }
        }
    }
}

@Composable
fun PokemonExtraDetails(title: String, items: ArrayList<String>?) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = title,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items?.let { pokemonTypes ->
            pokemonTypes.forEach {
                SuggestionChip(
                    modifier = Modifier.height(30.dp),
                    onClick = {},
                    label = { Text(it.capitalize(Locale.current), fontSize = 16.sp) }
                )
            }
        }
    }
}
