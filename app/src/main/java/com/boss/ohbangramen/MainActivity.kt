package com.boss.ohbangramen

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.boss.ohbangramen.R
import com.boss.ohbangramen.MenuNetwork.MenuItemNetwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var menuViewModel: MenuViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        menuViewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        setContent {
            //OhBangRamenTheme {}
            AppContent(sharedPreferences = sharedPreferences, menuViewModel = menuViewModel)
        }
    }
}


class MenuViewModel(application: Application) : AndroidViewModel(application) {
    private val database by lazy {
        Room.databaseBuilder(application, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }

    val menuItems: LiveData<List<MenuItemRoom>> = database.menuItemDao().getAll()

    //Just in case.
    fun clearDatabase() {
        viewModelScope.launch (Dispatchers.IO) {
            database.menuItemDao().clearAll()
        }
    }

    fun loadData(onLoadComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            database.menuItemDao().clearAll()
            if (
//                isNetworkAvailable() &&
                isMenuEmpty()) {
                val menuItemsNetwork = fetchMenu()
                saveMenuToDatabase(menuItemsNetwork)
            }
            onLoadComplete.invoke() // Notify when loading is complete
        }
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        viewModelScope.launch(Dispatchers.IO) {
            database.menuItemDao().insertAll(*menuItemRoom.toTypedArray())
        }
    }

    private suspend fun isMenuEmpty(): Boolean {
        return database.menuItemDao().isEmpty()
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val httpClient = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType.Application.Json)
                // For ContentType, use this bash command: curl -I https://whateverURL
                // Change to this if necessary: ContentType("text", "plain"))
            }
        }
        return httpClient.get("https://jamesserge.github.io/ohbang-json/data.json")//"https://jamesserge.github.io/ohbang-json/data.json")
            .body<MenuNetwork>()
            .menu
    }

    fun isNetworkAvailable(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            val process = Runtime.getRuntime().exec(command)
            val exitValue = process.waitFor()
            exitValue == 0
        } catch (e: Exception) {
            false
        }
    }

    fun getFilteredMenuItems(searchPhrase: String, category: String): LiveData<List<MenuItemRoom>> {
        return database.menuItemDao().getFilteredMenuItems("%$searchPhrase%", category)
    }

    fun getMenuItemById(itemId: Int): LiveData<List<MenuItemRoom>> {
        return database.menuItemDao().getMenuItemById(itemId)
    }
}

@Composable
fun AppContent(
    sharedPreferences: SharedPreferences,
    menuViewModel: MenuViewModel
) {
    val MyNavigation = rememberNavController()

    // State to track whether data loading is complete
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
//        menuViewModel.clearDatabase()
        // Perform initial data fetching
        menuViewModel.loadData {
            isLoading = false // Update state when loading is complete
        }
    }

    // Display loading screen while loading
    if (isLoading) {
        LoadingScreen()
    } else {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Navigation(navController = MyNavigation, sharedPreferences = sharedPreferences, menuViewModel = menuViewModel)
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Customize background color as needed
        contentAlignment = Alignment.Center
    ) {
        // Display your loading indicator or image here
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Loading Image",
            modifier = Modifier.size(200.dp)
        )
    }
}