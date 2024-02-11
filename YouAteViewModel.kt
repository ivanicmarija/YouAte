package hr.ferit.marijaivanic.youate.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class YouAteViewModel: ViewModel() {
    private val db = Firebase.firestore
    val foodData = mutableStateListOf<Food>()
    val profileData = mutableStateListOf<Profile>()

    private var userEmail: String = ""
    private var userPassword: String = ""

    init {
        fetchDatabaseData()
    }

    fun fetchDatabaseData() {
        db.collection("food")
            .get()
            .addOnSuccessListener { result ->
                for (data in result.documents) {
                    val food = data.toObject(Food::class.java)
                    if (food != null) {
                        food.id = data.id
                        foodData.add(food)
                    }
                }
            }

        db.collection("profile")
            .get()
            .addOnSuccessListener { result ->
                for (data in result.documents) {
                    val profile = data.toObject(Profile::class.java)
                    if (profile != null) {
                        profile.id = data.id
                        profileData.add(profile)
                    }
                }
            }
    }

    fun updateFood(food: Food) {
        db.collection("food")
            .document(food.id)
            .set(food)
    }

    fun signUp(
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            onError("Invalid email format")
            return
        }

        if (password != confirmPassword) {
            onError("Password do not match")
            return
        }

        val profile = Profile(email = email, password = password)
        db.collection("profile")
            .add(profile)
            .addOnSuccessListener { documentReference ->
                println("User signed up successfully")
                userEmail = email
                userPassword = password
                onSuccess()
            }
            .addOnFailureListener { e ->
                println("Error adding user to the profile database: ${e.message}")
                onError("Failed to sign up")
            }
    }

    fun logIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val profile = Profile(email = email, password = password)
        db.collection("profile")
            .add(profile)
            .addOnSuccessListener { documentReference ->
                userEmail = email
                userPassword = password
                onSuccess()
            }
            .addOnFailureListener { e ->
                println("Error adding user to the profile database: ${e.message}")
                onError("Failed to log in")
            }
    }

    fun getEmail(): String {
        return userEmail
    }

    fun getPassword(): String {
        return userPassword
    }

    fun clearCart() {
        foodData.forEach { it.isAddedToCart == false }
    }
}