package hr.ferit.marijaivanic.youate.ui

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
fun ProfileScreen(
    navigation: NavController,
    email: String,
    password: String
) {

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Linen)
            .fillMaxSize()
    ) {
        ProfileTopBar(navigation)
        Spacer(modifier = Modifier.padding(vertical = 40.dp))
        ProfilePicture()
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        ProfileInfo(
            email = email,
            password = password
        )
        Spacer(modifier = Modifier.padding(vertical = 80.dp))
        SignOutButton(navigation)
    }
}

@Composable
fun ProfilePicture() {
    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(White),
        modifier = Modifier
            .size(100.dp)
            .shadow(5.dp, shape = CircleShape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Profile",
                tint = DingyDungeon,
                modifier = Modifier
                    .size(50.dp)
            )
        }
    }
}

@Composable
fun ProfileInfo(
    email: String,
    password: String
) {
    Column {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "Email: $email",
                fontFamily = InterRegular,
                fontSize = 15.sp,
                color = DarkLiver
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "Password: $password",
                fontFamily = InterRegular,
                fontSize = 15.sp,
                color = DarkLiver
            )
        }
    }
}
@Composable
fun SignOutButton(
    navigation: NavController
) {
    Button(
        onClick = { navigation.navigate(Routes.SCREEN_SPLASH) },
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
            painter = painterResource(id = R.drawable.sign_out),
            contentDescription = "Sign out",
            modifier = Modifier
                .width(45.dp)
                .height(45.dp)
        )
    }
}

@Composable
fun ProfileTopBar(
    navigation: NavController
) {
    var containerBack by remember {
        mutableStateOf(White)
    }

    var contentBack by remember {
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
                Spacer(modifier = Modifier.padding(horizontal = 50.dp))
                CircularButton(
                    R.drawable.back_right,
                    containerColor = containerBack,
                    contentColor = contentBack,
                    onClick = {
                        containerBack = DingyDungeon
                        contentBack = White
                        navigation.navigateUp()
                    }
                )
            }
        }
    }
}