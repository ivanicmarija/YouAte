package hr.ferit.marijaivanic.youate.ui

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import hr.ferit.marijaivanic.youate.R
import hr.ferit.marijaivanic.youate.ui.theme.Black
import hr.ferit.marijaivanic.youate.ui.theme.BrinkPink
import hr.ferit.marijaivanic.youate.ui.theme.DarkLiver
import hr.ferit.marijaivanic.youate.ui.theme.DingyDungeon
import hr.ferit.marijaivanic.youate.ui.theme.InterRegular
import hr.ferit.marijaivanic.youate.ui.theme.Linen
import hr.ferit.marijaivanic.youate.ui.theme.Rajah
import hr.ferit.marijaivanic.youate.ui.theme.SuezOneRegular
import hr.ferit.marijaivanic.youate.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigation: NavController,
    viewModel: YouAteViewModel,
    onProfileClick: () -> Unit,
) {
    val scrollState = rememberLazyListState()
    Scaffold(
        bottomBar = {
            BottomBar(navigation = navigation)
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Linen)
        ) {
            item {
                TopBar(onProfileClick)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Title(
                        title = "What are you going to eat today?",
                        modifier = Modifier
                            .width(235.dp)
                            .height(50.dp),
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 50.dp))
                    Image(
                        painter = painterResource(id =  R.drawable.gradient_logo),
                        contentDescription = "LOGO",
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                SearchBar(labelText = "Search here")

                Image(
                    painter = painterResource(id = R.drawable.discount),
                    contentDescription = "Discount",
                    modifier = Modifier
                        .width(385.dp)
                        .height(148.dp)
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Title(
                    title = "Categories",
                    modifier = Modifier
                        .width(380.dp)
                        .height(25.dp)
                        .padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.padding(vertical = 5.dp))

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(categories.size) {
                        val category = categories[it]
                        CategoriesCard(
                            onClick = { navigation.navigate(Routes.getCategoryScreenPath(it)) },
                            category = category
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Title(
                    title = "Top recommendations",
                    modifier = Modifier
                        .width(380.dp)
                        .height(25.dp)
                        .padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.padding(vertical = 5.dp))

                val recommendedFoods = viewModel.foodData.filter { it.isRecommended }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(recommendedFoods.size) { it ->
                        val food = recommendedFoods.getOrNull(it)
                        if (food != null) {
                            RecommendedCard(
                                onClick = { navigation.navigate(Routes.getFoodDetailPath(it.id)) },
                                iconResource = food.foodImage,
                                food = food
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Image(
                    painter = painterResource(id = R.drawable.special_offer),
                    contentDescription = "Discount",
                    modifier = Modifier
                        .width(400.dp)
                        .height(200.dp)
                        .background(DingyDungeon)
                )
                Spacer(modifier = Modifier.padding(vertical = 40.dp))
            }
        }
    }
}

@Composable
fun Title(
    title: String,
    modifier: Modifier
) {
    Text(
        text = title,
        fontFamily = SuezOneRegular,
        fontSize = 20.sp,
        modifier = modifier,
        color = DarkLiver
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    labelText: String,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = White,
        placeholderColor = DarkLiver,
        textColor = Black,
        unfocusedLabelColor = Color.DarkGray,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
) {
    var textInput by remember{ mutableStateOf("") }
    TextField(
        value = textInput,
        onValueChange = {textInput = it},
        label = { Text(text = labelText) },
        shape = RoundedCornerShape(15.dp),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),
                tint = DingyDungeon
            )
        },
        colors = colors,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(elevation = 10.dp)
    )
}

@Composable
fun CategoriesCard(
    onClick: () -> Unit,
    category: Category
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .clickable { onClick() }
            .padding(10.dp)
            .shadow(elevation = 15.dp, shape = RoundedCornerShape(15.dp))
            .size(70.dp),
        colors = CardDefaults.cardColors(White),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = category.categoryIcon),
                contentDescription = "Category Icon",
                modifier = Modifier.size(40.dp),
                tint = DingyDungeon
            )
        }
    }
}

@Composable
fun RecommendedCard(
    onClick: (food: Food) -> Unit,
    iconResource: String,
    food: Food
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .clickable { onClick(food) }
            .padding(10.dp),
        colors = CardDefaults.cardColors(Color.Transparent)
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
                    painter = rememberAsyncImagePainter(model = iconResource),
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

@Composable
fun TopBar(
    onProfileClick: () -> Unit
) {
    var containerUser by remember {
        mutableStateOf(White)
    }

    var contentUser by remember {
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
                    R.drawable.user,
                    containerColor = containerUser,
                    contentColor = contentUser,
                    onClick = {
                        containerUser = DingyDungeon
                        contentUser = White
                        onProfileClick()
                    }
                )
            }
        }
    }
}