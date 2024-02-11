package hr.ferit.marijaivanic.youate.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.ferit.marijaivanic.youate.R
import hr.ferit.marijaivanic.youate.ui.theme.BrinkPink
import hr.ferit.marijaivanic.youate.ui.theme.LibreFranklinBlack
import hr.ferit.marijaivanic.youate.ui.theme.Linen
import hr.ferit.marijaivanic.youate.ui.theme.Rajah
import hr.ferit.marijaivanic.youate.ui.theme.Salmon
import hr.ferit.marijaivanic.youate.ui.theme.White

@Composable
fun LogInScreen(
    viewModel: YouAteViewModel,
    navigation: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Linen)
    ) {
        ScreenTitleLogo()
        Spacer(modifier = Modifier.padding(50.dp))
        LogInForm(
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPasswordChange = { password = it },
            navigation = navigation,
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInForm(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    navigation: NavController,
    viewModel: YouAteViewModel
) {
    Column(
        modifier = Modifier
            .background(
                color = White,
                shape = RoundedCornerShape(20.dp)
            )
            .width(325.dp)
            .height(530.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        TopButtonsLogIn(navigation)
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Enter email") },
            modifier = Modifier
                .width(300.dp)
                .padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Enter password") },
            modifier = Modifier
                .width(300.dp)
                .padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
        )
        Spacer(modifier = Modifier.padding(85.dp))
        LogInButton(
            email = email,
            password = password,
            navigation = navigation,
            viewModel = viewModel
        )
    }
}

@Composable
fun TopButtonsLogIn(
    navigation: NavController
) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(100.dp)
            .padding(top = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = { navigation.navigate(Routes.SCREEN_SIGN_UP) },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .background(White),
                shape = RoundedCornerShape(70.dp)
            ) {
                Text(
                    text = "SIGN UP",
                    fontFamily = LibreFranklinBlack,
                    fontSize = 18.sp,
                    style = TextStyle(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Rajah, Salmon, BrinkPink)
                        )
                    )
                )
            }

            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Rajah,
                                Salmon,
                                BrinkPink
                            )
                        ),
                        shape = RoundedCornerShape(70.dp),
                    )
            ) {
                Text(
                    text = "LOG IN",
                    fontFamily = LibreFranklinBlack,
                    fontSize = 18.sp,
                    color = White
                )
            }
        }
    }
}

@Composable
fun LogInButton(
    email: String,
    password: String,
    navigation: NavController,
    viewModel: YouAteViewModel
) {
    Button(
        onClick = {
            viewModel.logIn(
                email,
                password,
                onSuccess = {
                    navigation.navigate(Routes.SCREEN_HOME)
                },
                onError = {errorMessage ->

                }
            )
        },
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .shadow(elevation = 15.dp, shape = CircleShape)
            .width(80.dp)
            .height(80.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Rajah,
                        Salmon,
                        BrinkPink
                    )
                ),
                shape = CircleShape
            )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.sign_in),
            contentDescription = "Sign in",
            modifier = Modifier
                .width(45.dp)
                .height(45.dp)
        )
    }
}