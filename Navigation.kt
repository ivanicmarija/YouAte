package hr.ferit.marijaivanic.youate.ui

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation

object Routes {
    const val SCREEN_SPLASH = "splash_screen"
    const val SCREEN_SIGN_UP = "sign_up_screen"
    const val SCREEN_LOG_IN = "log_in_screen"
    const val SCREEN_HOME = "home_screen"
    const val SCREEN_CATEGORIES = "categoriesScreen/{selectedCategory}"
    const val SCREEN_DETAIL_FOOD = "foodDetail/{foodId}"
    const val SCREEN_PROFILE = "profile_screen"
    const val SCREEN_CART = "cart"
    const val SCREEN_FAVOURITE = "favourite"

    fun getCategoryScreenPath(selectedCategory: Int?): String {
        if (selectedCategory != null && selectedCategory != -1) {
            return "categoriesScreen/$selectedCategory"
        }
        return "categoriesScreen/$selectedCategory"
    }

    fun getFoodDetailPath(foodId: String?): String {
        if (foodId != null) {
            return "foodDetail/$foodId"
        }
        return "foodDetail/$foodId"
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationController(
    viewModel: YouAteViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SCREEN_SPLASH
    ) {
        composable(Routes.SCREEN_SPLASH) {
            SplashScreen(
                onGetStartedClick = {
                    navController.navigate(Routes.SCREEN_SIGN_UP)
                }
            )
        }

        composable(Routes.SCREEN_SIGN_UP) {
            SignUpScreen(
                navigation = navController,
                viewModel = viewModel
            )
        }

        composable(Routes.SCREEN_LOG_IN) {
            LogInScreen(
                viewModel = viewModel,
                navigation = navController
            )
        }

        composable(Routes.SCREEN_HOME) {
            HomeScreen(
                navigation = navController,
                viewModel = viewModel,
                onProfileClick = {
                    navController.navigate(Routes.SCREEN_PROFILE)
                }
            )
        }

        composable(
            Routes.SCREEN_CATEGORIES,
            arguments = listOf(
                navArgument("selectedCategory") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("selectedCategory")?.let {
                CategoriesScreen(
                    viewModel = viewModel,
                    navigation = navController,
                    selectedCategory = it
                )
            }
        }

        composable(Routes.SCREEN_DETAIL_FOOD,
            arguments = listOf(
                navArgument("foodId") {
                    type = NavType.StringType
                }
            )
        ) {backStackEntry ->
            backStackEntry.arguments?.getString("foodId")?.let {
                FoodDetailScreen(
                    navigation = navController,
                    viewModel = viewModel,
                    foodId = it
                )
            }
        }

        composable(Routes.SCREEN_PROFILE) {
            val email = viewModel.getEmail()
            val password = viewModel.getPassword()
            ProfileScreen(
                navigation = navController,
                email = email,
                password = password
            )
        }

        composable(Routes.SCREEN_FAVOURITE) {
            FavouritesScreen(
                navigation = navController,
                viewModel = viewModel
            )
        }

        composable(Routes.SCREEN_CART) {
            CartScreen(
                navigation = navController,
                viewModel = viewModel
            )
        }

    }
}