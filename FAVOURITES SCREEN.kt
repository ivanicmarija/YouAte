package hr.ferit.marijaivanic.youate.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import hr.ferit.marijaivanic.youate.R
import hr.ferit.marijaivanic.youate.ui.theme.BrinkPink
import hr.ferit.marijaivanic.youate.ui.theme.DarkLiver
import hr.ferit.marijaivanic.youate.ui.theme.InterRegular
import hr.ferit.marijaivanic.youate.ui.theme.Linen
import hr.ferit.marijaivanic.youate.ui.theme.Rajah
import hr.ferit.marijaivanic.youate.ui.theme.SuezOneRegular
import hr.ferit.marijaivanic.youate.ui.theme.White

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    navigation: NavController,
    viewModel: YouAteViewModel
) {
    Scaffold(
        bottomBar = {
            BottomBar(navigation = navigation)
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Linen)
        ) {
            Text(
                text = "Favourites",
                fontFamily = SuezOneRegular,
                fontSize = 30.sp,
                color = DarkLiver,
                modifier = Modifier
                    .padding(10.dp)
            )
            val favouritedFoods = viewModel.foodData.filter { it.isFavorited }
            val scrollState = rememberLazyGridState()
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = scrollState,
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp)
                    .background(color = Linen)
            ) {
                items(favouritedFoods.size) { index ->
                    val food = favouritedFoods.getOrNull(index)
                    if (food != null) {
                        FavouritesCard(
                            food = food,
                            onClick = {
                                navigation.navigate(Routes.getFoodDetailPath(food.id))
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun FavouritesCard(
    food: Food,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .clickable { onClick() }
            .padding(10.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
    ) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(210.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            White,
                            White
                        )
                    )
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = food.foodImage),
                    contentDescription = food.title,
                    modifier = Modifier
                        .size(100.dp)
                        .zIndex(1f)
                )

                Spacer(modifier = Modifier.padding(vertical = 5.dp))

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .height(25.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "Rating",
                        tint = BrinkPink,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 15.dp))
                    Text(
                        text = food.rating,
                        fontSize = 17.sp,
                        color = DarkLiver,
                        fontFamily = InterRegular
                    )
                }

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .height(25.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.money),
                        contentDescription = "Price",
                        tint = Rajah,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 15.dp))
                    Text(
                        text = "€ ${String.format("%.2f", food.price)}",
                        fontSize = 17.sp,
                        color = DarkLiver,
                        fontFamily = InterRegular
                    )
                }
            }
        }
    }
}