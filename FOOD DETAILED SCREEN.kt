package hr.ferit.marijaivanic.youate.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import hr.ferit.marijaivanic.youate.R
import hr.ferit.marijaivanic.youate.ui.theme.BrinkPink
import hr.ferit.marijaivanic.youate.ui.theme.DarkLiver
import hr.ferit.marijaivanic.youate.ui.theme.DingyDungeon
import hr.ferit.marijaivanic.youate.ui.theme.InterBlack
import hr.ferit.marijaivanic.youate.ui.theme.InterRegular
import hr.ferit.marijaivanic.youate.ui.theme.LibreFranklinBlack
import hr.ferit.marijaivanic.youate.ui.theme.LightSalmonPink
import hr.ferit.marijaivanic.youate.ui.theme.Linen
import hr.ferit.marijaivanic.youate.ui.theme.Rajah
import hr.ferit.marijaivanic.youate.ui.theme.Salmon
import hr.ferit.marijaivanic.youate.ui.theme.White

@Composable
fun FoodDetailScreen(
    navigation: NavController,
    viewModel: YouAteViewModel,
    foodId: String,
) {
    val foodItem = viewModel.foodData.first { it.id == foodId}
    val scrollState = rememberLazyListState()

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightSalmonPink)
    ) {
        item {
            DetailTopBar(
                navigation = navigation,
                viewModel = viewModel,
                food = foodItem
            )
            FoodImage(iconResource = foodItem.foodImage)
            Detail(food = foodItem)
            AddToCart(
                food = foodItem,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun FoodImage(
    iconResource: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(212.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Transparent,
                        White
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = iconResource),
            contentDescription = null,
            modifier = Modifier.size(400.dp)
        )
    }
}

@Composable
fun Detail(
    food: Food
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(375.dp)
            .background(White)
    ) {
        ItemTitle(food = food)
        ItemDescription(food = food)
        BasicInfo(food = food)
    }
}

@Composable
fun ItemTitle(
    food: Food
) {
    Text(
        text = food.title,
        fontFamily = InterBlack,
        fontSize = 30.sp,
        color = DarkLiver,
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
fun ItemDescription(
    food: Food
) {
    Text(
        text = food.description,
        fontFamily = InterRegular,
        fontSize = 15.sp,
        textAlign = TextAlign.Justify,
        fontWeight = FontWeight.SemiBold,
        color = DarkLiver,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .padding(10.dp)
    )
}

@Composable
fun InfoColumn(
    iconResource: Int,
    text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = null,
            tint = DingyDungeon,
            modifier = Modifier
                .height(24.dp)
        )

        Spacer(modifier = Modifier.padding(vertical = 5.dp))

        Text(
            text = text,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BasicInfo(
    food: Food
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        InfoColumn(R.drawable.money, "€ ${String.format("%.2f", food.price)}")
        InfoColumn(R.drawable.star, food.rating)
        InfoColumn(R.drawable.clock, food.deliveryTime)
    }
}

@Composable
fun AddToCart(
    food: Food,
    viewModel: YouAteViewModel
) {
    var quantity by remember { mutableIntStateOf(1) }

    Box(
        modifier = Modifier
            .height(275.dp)
            .background(Linen)
            .padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Amount",
                modifier = Modifier
                    .fillMaxWidth()
                    .width(25.dp),
                fontFamily = InterBlack,
                fontSize = 15.sp,
                color = DarkLiver
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "€ ${String.format("%.2f", food.price * quantity)}",
                    color = DarkLiver,
                    fontFamily = InterBlack,
                    fontSize = 50.sp
                )
                Spacer(modifier = Modifier.padding(horizontal = 30.dp))
                Amounts(
                    initialAmount = quantity
                ) { newQuantity ->
                    quantity = newQuantity
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 30.dp))
            AddToCartButton(
                food = food,
                viewModel = viewModel,
                quantity = quantity
            )
        }
    }
}

@Composable
fun Amounts(
    initialAmount: Int,
    onAmountChange: (Int) -> Unit
) {
    var amount by remember { mutableIntStateOf(initialAmount) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .width(150.dp)
            .height(75.dp)
    ) {
        CircularButton(
            iconResource = R.drawable.minus,
            contentColor = DingyDungeon,
            containerColor = White
        ) {
            if (amount > 1) {
                amount--
                onAmountChange(amount)
            }
        }

        Text(
            text = "$amount",
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            color = DingyDungeon
        )

        CircularButton(iconResource = R.drawable.plus,
            contentColor = DingyDungeon,
            containerColor = White
        ) {
            amount++
            onAmountChange(amount)
        }
    }
}

@Composable
fun CircularButton(
    @DrawableRes iconResource : Int,
    containerColor : Color,
    contentColor : Color,
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(12.dp),
    onClick : () -> Unit = {}
) {
    Button(
        onClick = { onClick() },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = elevation,
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .size(40.dp)
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = null,
        )
    }
}

@Composable
fun AddToCartButton(
    food: Food,
    viewModel: YouAteViewModel,
    quantity: Int
) {
    Button(
        onClick = {
            food.isAddedToCart = !food.isAddedToCart
            food.totalPrice = food.price*quantity
            food.quantity = quantity
            viewModel.updateFood(food)
        },
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .shadow(elevation = 15.dp, shape = RoundedCornerShape(50.dp))
            .width(200.dp)
            .height(50.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Rajah,
                        Salmon,
                        BrinkPink
                    )
                ),
                shape = RoundedCornerShape(50.dp)
            )
    ) {
        Text(
            text = "ADD TO CART",
            fontFamily = LibreFranklinBlack,
            fontSize = 18.sp,
            color = White
        )
    }
}

@Composable
fun DetailTopBar(
    navigation: NavController,
    viewModel: YouAteViewModel,
    food: Food
) {
    var containerBack by remember {
        mutableStateOf(White)
    }

    var contentBack by remember {
        mutableStateOf(DingyDungeon)
    }

    var containerHeart by remember {
        mutableStateOf(White)
    }

    var contentHeart by remember {
        mutableStateOf(DingyDungeon)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.Transparent)
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
                    R.drawable.heart,
                    containerColor = if (food.isFavorited) DingyDungeon else containerHeart,
                    contentColor = if (food.isFavorited) White else contentHeart
                ) {
                    containerHeart = contentHeart
                    contentHeart = containerHeart
                    food.isFavorited = !food.isFavorited
                    viewModel.updateFood(food)
                }
            }
        }
    }
}