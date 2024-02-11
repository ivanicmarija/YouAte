package hr.ferit.marijaivanic.youate.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.ferit.marijaivanic.youate.R
import hr.ferit.marijaivanic.youate.ui.theme.BrinkPink
import hr.ferit.marijaivanic.youate.ui.theme.InterRegular
import hr.ferit.marijaivanic.youate.ui.theme.LibreFranklinBlack
import hr.ferit.marijaivanic.youate.ui.theme.Rajah
import hr.ferit.marijaivanic.youate.ui.theme.Salmon
import hr.ferit.marijaivanic.youate.ui.theme.White


@Composable
fun SplashScreen(
    onGetStartedClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        BrinkPink,
                        Salmon,
                        Rajah
                    )
                )
            )
            .padding(top = 90.dp, bottom = 90.dp)
    ) {
        Logo()
        Spacer(modifier = Modifier.padding(bottom = 90.dp))
        ScreenTitle()
        ScreenSubtitle(subtitle = "and left no crumbs")
        Spacer(modifier = Modifier.padding(bottom = 280.dp))
        GetStartedButton(onGetStartedClick)
    }
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(id =  R.drawable.logo),
        contentDescription = "LOGO",
        modifier = Modifier
            .width(90.dp)
            .height(80.dp)
    )
}

@Composable
fun ScreenTitle() {
    Image(
        painter = painterResource(id = R.drawable.youate),
        contentDescription = "YOUATE",
        modifier = Modifier
            .width(266.dp)
            .height(90.dp)
    )
}

@Composable
fun ScreenSubtitle(
    subtitle : String
) {
    Text(
        text = subtitle,
        textAlign = TextAlign.Center,
        style = TextStyle(
            color = White,
            fontSize = 30.sp,
            fontFamily = InterRegular
        )
    )
}

@Composable
fun GetStartedButton(
    onGetStartedClick: () -> Unit
) {
    Button(
        onClick = {onGetStartedClick()},
        shape = RoundedCornerShape(70.dp),
        colors = ButtonDefaults.buttonColors(containerColor = White),
        modifier = Modifier
            .shadow(elevation = 20.dp)
            .width(200.dp)
            .height(55.dp)
    ) {
        Text(
            text = "GET STARTED",
            fontFamily = LibreFranklinBlack,
            fontSize = 18.sp,
            style = TextStyle(
                brush = Brush.horizontalGradient(
                    colors = listOf(Rajah, Salmon, BrinkPink)
                )
            )
        )
    }
}