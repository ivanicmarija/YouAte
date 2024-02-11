package hr.ferit.marijaivanic.youate.ui

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hr.ferit.marijaivanic.youate.R
import hr.ferit.marijaivanic.youate.ui.theme.DarkLiver
import hr.ferit.marijaivanic.youate.ui.theme.DingyDungeon

data class BottomNavigationItem(
    val selectedIcon: Int,
    val unselectedIcon: Int
)

val items = listOf<BottomNavigationItem>(
    BottomNavigationItem(
        selectedIcon = R.drawable.home_filled,
        unselectedIcon = R.drawable.home
    ),

    BottomNavigationItem(
        selectedIcon = R.drawable.cart_filled,
        unselectedIcon = R.drawable.cart
    ),

    BottomNavigationItem(
        selectedIcon = R.drawable.heart_filled,
        unselectedIcon = R.drawable.heart
    ),
)

@Composable
fun BottomBar(
    navigation: NavController
) {
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val navBackStackEntry by navigation.currentBackStackEntryAsState()

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    when (index) {
                        0 -> navigation.navigate(Routes.SCREEN_HOME)
                        1 -> navigation.navigate(Routes.SCREEN_CART)
                        2 -> navigation.navigate(Routes.SCREEN_FAVOURITE)
                    }
                },
                icon = {
                    if (index == selectedItemIndex) {
                        Icon(
                            painter = painterResource(id = item.selectedIcon),
                            contentDescription = null,
                            tint = DingyDungeon
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = item.unselectedIcon),
                            contentDescription = null,
                            tint = DarkLiver
                        )
                    }
                }
            )
        }
    }
    val currentRoute = navBackStackEntry?.destination?.route
    selectedItemIndex = when (currentRoute) {
        Routes.SCREEN_HOME -> 0
        Routes.SCREEN_CART -> 1
        Routes.SCREEN_FAVOURITE -> 2
        else -> selectedItemIndex
    }
}