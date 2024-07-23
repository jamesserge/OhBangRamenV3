package com.boss.ohbangramen

import androidx.navigation.NavHostController

interface Destinations {
    fun Home()
    fun Profile()
    fun Onboarding()
}

object HomeScreen : Destinations {
    override fun Home() {
        TODO("Not yet implemented")
    }
    override fun Profile() {
        TODO("Not yet implemented")
    }
    override fun Onboarding() {
        TODO("Not yet implemented")
    }
}

object ProfileScreen : Destinations {
    override fun Home() {
        TODO("Not yet implemented")
    }
    override fun Profile() {
        TODO("Not yet implemented")
    }
    override fun Onboarding() {
        TODO("Not yet implemented")
    }
}

object OnboardingScreen : Destinations {
    override fun Home() {
        TODO("Not yet implemented")
    }
    override fun Profile() {
        TODO("Not yet implemented")
    }
    override fun Onboarding() {
        TODO("Not yet implemented")
    }
}

class NavigationHandler (private val navController: NavHostController) : Destinations {
    override fun Home() {
        navController.navigate("home")
    }
    override fun Profile() {
        navController.navigate("profile")
    }
    override fun Onboarding() {
        navController.navigate("onboarding")
    }
}