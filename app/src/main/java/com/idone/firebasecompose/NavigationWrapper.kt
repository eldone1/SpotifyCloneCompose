package com.idone.firebasecompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.idone.firebasecompose.presentation.home.HomeScreen
import com.idone.firebasecompose.presentation.initial.InitialScreen
import com.idone.firebasecompose.presentation.login.LoginScreen
import com.idone.firebasecompose.presentation.signup.SignupScreen


@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth,
    db : FirebaseFirestore
) {

    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navHostController.navigate("logIn") },
                navigateToSignUp = { navHostController.navigate("signUp") })
        }

        composable("logIn") {
            LoginScreen(auth){ navHostController.navigate("home") }
        }
        composable("signUp") {
            SignupScreen(auth)
        }
        composable("home") {
            HomeScreen(db)
        }
    }
}
