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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import hr.ferit.marijaivanic.youate.R
import hr.ferit.marijaivanic.youate.ui.theme.BrinkPink
import hr.ferit.marijaivanic.youate.ui.theme.DarkLiver
import hr.ferit.marijaivanic.youate.ui.theme.DingyDungeon
import hr.ferit.marijaivanic.youate.ui.theme.InterBlack
import hr.ferit.marijaivanic.youate.ui.theme.InterRegular
import hr.ferit.marijaivanic.youate.ui.theme.LibreFranklinBlack
import hr.ferit.marijaivanic.youate.ui.theme.LightSilver
import hr.ferit.marijaivanic.youate.ui.theme.Linen
import hr.ferit.marijaivanic.youate.ui.theme.Rajah
import hr.ferit.marijaivanic.youate.ui.theme.Salmon
import hr.ferit.marijaivanic.youate.ui.theme.SuezOneRegular
import hr.ferit.marijaivanic.youate.ui.theme.White


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navigation: NavController,
    viewModel: YouAteViewModel
) {
    val deliveryPrice = 1.50
    var cartFoods by remember { mutableStateOf(viewModel.foodData.filter { it.isAddedToCart }) }
    var subtotal by remember(cartFoods) { mutableDoubleStateOf(cartFoods.sumOf { it.price * it.quantity }) }
    var totalPrice = subtotal + deliveryPrice

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
                .padding(10.dp)
        ) {
            Location()
            val scrollState = rememberLazyListState()
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                state = scrollState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(color = Linen)
            ) {
                items(cartFoods.size) { index ->
                    val food = cartFoods.getOrNull(index)
                    if (food != null) {
                        CartCard(
                            food = food,
                            viewModel = viewModel,
                            onClick = {
                                navigation.navigate(Routes.getFoodDetailPath(food.id))
                            },
                            onAmountChange = { newAmount ->
                                val newSubtotal = cartFoods.sumOf { it.price * it.quantity }
                                subtotal = newSubtotal
                            }
                        )
                    }
                }
            }
            OrderSummary(
                initialSubtotal = subtotal,
                deliveryPrice = deliveryPrice,
                initialTotalPrice = totalPrice,
                navigation = navigation,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun CartCard(
    food: Food,
    viewModel: YouAteViewModel,
    onClick: () -> Unit,
    onAmountChange: (Double) -> Unit,
) {
    var quantity by remember { mutableIntStateOf(food.quantity) }
    var total by remember { mutableStateOf(food.price * quantity) }
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
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Title(
                    title = food.title,
                    modifier = Modifier
                        .width(180.dp)
                        .height(50.dp)
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
                        text = "€ ${String.format("%.2f", total)}",
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
                        .padding(start = 10.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
                ) {
                    CartAmounts(
                        initialAmount = quantity
                    ) {newQuantity ->
                        food.quantity = newQuantity
                        total = food.price * newQuantity
                        if(newQuantity == 0) {
                            food.isAddedToCart = false
                            viewModel.updateFood(food)
                        }
                        onAmountChange(total)
                    }
                }
            }

            Image(
                painter = rememberAsyncImagePainter(model = food.foodImage),
                contentDescription = "Food image",
                modifier = Modifier
                    .size(150.dp)
                    .align(CenterVertically)
            )
        }
    }
}

@Composable
fun CartAmounts(
    initialAmount: Int,
    onQuantityChange: (Int) -> Unit
) {
    var amount by remember { mutableIntStateOf(initialAmount) }

    Row(
        verticalAlignment = CenterVertically,
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
            if (amount > 0) {
                amount--
                onQuantityChange(amount)
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
            onQuantityChange(amount)
        }
    }
}

@Composable
fun Location() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.location),
            contentDescription = "Location",
            tint = DarkLiver,
            modifier = Modifier
                .padding(10.dp)
        )
        Text(
            text = "Osijek, Croatia",
            fontFamily = SuezOneRegular,
            fontSize = 15.sp,
            color = DarkLiver,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}

@Composable
fun OrderSummary(
    initialSubtotal: Double,
    deliveryPrice: Double,
    initialTotalPrice: Double,
    navigation: NavController,
    viewModel: YouAteViewModel,
) {
    Column(
        modifier = Modifier
            .width(320.dp)
            .background(Linen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Order Summary",
            fontFamily = SuezOneRegular,
            fontSize = 15.sp,
            color = DarkLiver,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        Card(
            modifier = Modifier
                .width(350.dp),
            colors = CardDefaults.cardColors(LightSilver),
            shape = RoundedCornerShape(15.dp)
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(
                        text = "Subtotal:",
                        fontFamily = SuezOneRegular,
                        fontSize = 15.sp,
                        color = DarkLiver,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    Text(
                        text = "€ ${String.format("%.2f", initialSubtotal)}",
                        fontFamily = InterRegular,
                        color = DarkLiver,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(
                        text = "Delivery:",
                        fontFamily = SuezOneRegular,
                        fontSize = 15.sp,
                        color = DarkLiver,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    Text(
                        text = "€ ${String.format("%.2f", deliveryPrice)}",
                        fontFamily = InterRegular,
                        fontSize = 15.sp,
                        color = DarkLiver,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Divider(
                        thickness = 1.dp,
                        color = DarkLiver,
                        modifier = Modifier.width(300.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "TOTAL",
                        fontFamily = SuezOneRegular,
                        fontSize = 20.sp,
                        color = DarkLiver,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    Text(
                        text = "€ ${String.format("%.2f", initialTotalPrice)}",
                        fontFamily = InterRegular,
                        fontSize = 20.sp,
                        color = DarkLiver,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }

            }
        }
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        PurchaseButton(
            navigation = navigation,
            viewModel = viewModel
        )
    }
}

@Composable
fun PurchaseButton(
    navigation: NavController,
    viewModel: YouAteViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        PurchaseDialog(
            onBackToHome = {
                showDialog = false
                navigation.navigate(Routes.SCREEN_HOME)
            },
            viewModel = viewModel
        )
    }
    Button(
        onClick = { showDialog = true },
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(50.dp))
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
            ),
    ) {
        Text(
            text = "PURCHASE",
            fontFamily = LibreFranklinBlack,
            fontSize = 18.sp,
            color = White
        )
    }
}

@Composable
fun PurchaseDialog(
    onBackToHome: () -> Unit,
    viewModel: YouAteViewModel
) {
    Dialog(onDismissRequest = { onBackToHome() }) {
        Box(
            modifier = Modifier
                .background(White)
                .padding(16.dp)
                .size(350.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(300.dp),
                    colors = CardDefaults.cardColors(LightSilver),
                    shape = RoundedCornerShape(15.dp),
                )  {
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.gradient_logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Text(
                        text = "Yay! \n" +
                                "Your order has been purchased",
                        fontSize = 20.sp,
                        fontFamily = InterBlack,
                        color = DarkLiver,
                        modifier = Modifier
                            .width(175.dp)
                            .padding(vertical = 16.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Button(
                        onClick = {
                            viewModel.clearCart()
                            onBackToHome()
                        },
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        modifier = Modifier
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
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "BACK TO HOME",
                            fontFamily = LibreFranklinBlack,
                            fontSize = 18.sp,
                            color = White
                        )
                    }
                }
            }
        }
    }
}