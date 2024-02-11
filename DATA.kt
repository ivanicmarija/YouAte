package hr.ferit.marijaivanic.youate.ui

import androidx.annotation.DrawableRes
import hr.ferit.marijaivanic.youate.R

data class Food(
    var id: String = "",
    val foodImage: String = "",
    val title: String = "",
    var category: Int = 0,
    val rating: String = "",
    var price: Double = 0.0,
    val deliveryTime: String = "",
    val description: String = "",
    var isRecommended: Boolean = false,
    var isFavorited: Boolean = false,
    var isAddedToCart: Boolean = false,
    var quantity: Int = 0,
    var totalPrice: Double = 0.0
)

data class Category (
    val categoryId: Int,
    val category: String,
    @DrawableRes val categoryIcon: Int
)

data class Profile (
    var id: String = "",
    var email: String = "",
    var password: String = ""
)

val categories: List<Category> = listOf(
    Category(
        categoryId = 0,
        category = "Burger",
        categoryIcon = R.drawable.burger
    ),

    Category(
        categoryId = 1,
        category = "Pizza",
        categoryIcon = R.drawable.pizza
    ),

    Category(
        categoryId = 2,
        category = "Pasta",
        categoryIcon = R.drawable.pasta
    ),

    Category(
        categoryId = 3,
        category = "Chicken",
        categoryIcon = R.drawable.chicken
    ),

    Category(
        categoryId = 4,
        category = "Salads",
        categoryIcon = R.drawable.salad
    ),

    Category(
        categoryId = 5,
        category = "Sides",
        categoryIcon = R.drawable.fries
    ),

    Category(
        categoryId = 6,
        category = "Dessert",
        categoryIcon = R.drawable.dessert
    )
)