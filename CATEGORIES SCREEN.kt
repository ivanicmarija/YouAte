package hr.ferit.marijaivanic.youate.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import hr.ferit.marijaivanic.youate.R
import hr.ferit.marijaivanic.youate.ui.theme.BrinkPink
import hr.ferit.marijaivanic.youate.ui.theme.DarkLiver
import hr.ferit.marijaivanic.youate.ui.theme.DingyDungeon
import hr.ferit.marijaivanic.youate.ui.theme.InterRegular
import hr.ferit.marijaivanic.youate.ui.theme.Linen
import hr.ferit.marijaivanic.youate.ui.theme.Rajah
import hr.ferit.marijaivanic.youate.ui.theme.Salmon
import hr.ferit.marijaivanic.youate.ui.theme.White

@Composable
fun CategoriesScreen(
    viewModel: YouAteViewModel,
    navigation: NavController,
    selectedCategory: Int
) {
    val scrollState = rememberLazyListState()
    val categoryItems = viewModel.foodData.filter { it.category == selectedCategory }

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Linen)
    ) {
        item {
            CategoryTopBar(navigation = navigation)
            categoryItems.forEach { food ->
                FoodItemCard(
                    food = food,
                    onClick = {
                        navigation.navigate(Routes.getFoodDetailPath(food.id))
                    }
                )
            }
        }
    }
}

@Composable
fun FoodItemCard(
    food: Food,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp, topEnd = 100.dp, bottomEnd = 100.dp),
        modifier = Modifier
            .clickable { onClick() }
            .padding(10.dp)
            .width(360.dp)
            .shadow(
                10.dp,
                shape = RoundedCornerShape(
                    topStart = 15.dp,
                    bottomStart = 15.dp,
                    topEnd = 100.dp,
                    bottomEnd = 100.dp
                )
            ),
        colors = CardDefaults.cardColors(White)
    ) {
        Row {
            Column {
                Title(
                    title = food.title,
                    modifier = Modifier
                        .width(180.dp)
                        .padding(top = 10.dp, start = 20.dp, bottom = 5.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .width(180.dp)
                        .padding(start = 20.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
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
                        fontFamily = InterRegular,
                        modifier = Modifier
                            .padding(start = 20.dp, bottom = 5.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .width(180.dp)
                        .padding(start = 20.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = "Delivery time",
                        tint = Salmon,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 15.dp))
                    Text(
                        text = food.deliveryTime,
                        fontSize = 17.sp,
                        color = DarkLiver,
                        fontFamily = InterRegular,
                        modifier = Modifier
                            .padding(start = 20.dp, bottom = 5.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .width(180.dp)
                        .padding(start = 20.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
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
                        fontFamily = InterRegular,
                        modifier = Modifier
                            .padding(start = 20.dp, bottom = 5.dp)
                    )
                }
            }

            Image(
                painter = rememberAsyncImagePainter(model = food.foodImage),
                contentDescription = "Food image",
                modifier = Modifier.size(150.dp)
            )
        }
    }
}

@Composable
fun CategoryTopBar(
    navigation: NavController
) {
    var containerBack by remember {
        mutableStateOf(White)
    }

    var contentBack by remember {
        mutableStateOf(DingyDungeon)
    }

    var containerCart by remember {
        mutableStateOf(White)
    }

    var contentCart by remember {
        mutableStateOf(DingyDungeon)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        White,
                        Color.Transparent
                    )
                )
            )
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(56.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                CircularButton(
                    R.drawable.back_arrow,
                    containerColor = containerBack,
                    contentColor = contentBack,
                    onClick = {
                        containerBack = DingyDungeon
                        contentBack = White
                        navigation.navigateUp()
                    }
                )
                CircularButton(
                    R.drawable.cart,
                    containerColor = containerCart,
                    contentColor = contentCart,
                    onClick = {
                        containerCart = DingyDungeon
                        contentCart = White
                        navigation.navigate(Routes.SCREEN_CART)
                    }
                )
            }
        }
    }
}