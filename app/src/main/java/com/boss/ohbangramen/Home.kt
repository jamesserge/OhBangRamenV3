package com.boss.ohbangramen

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.boss.ohbangramen.R

@Composable
fun Home(navController: NavHostController, sharedPreferences: SharedPreferences, menuViewModel: MenuViewModel) {

    var searchPhrase by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    val filteredMenuItems by menuViewModel.getFilteredMenuItems(searchPhrase, selectedCategory).observeAsState(
        emptyList()
    )

    val focusManager = LocalFocusManager.current

    // State for network error notification
    var networkError by remember { mutableStateOf(false) }

    // Perform network connectivity check
//    LaunchedEffect(Unit) {
//        val isNetworkAvailable = menuViewModel.isNetworkAvailable()
//        if (!isNetworkAvailable) {
//            networkError = true
//        }
//    }
    // Perform network connectivity check

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
                detectVerticalDragGestures(
                    onDragStart = {
                        focusManager.clearFocus()
                    },
                    onVerticalDrag = { change, dragAmount ->
                        // Handle vertical drag if needed
                        change.consume()
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        ) {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(120.dp)
                        .width(180.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .clickable { navController.navigate("profile") }
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier
                                .background(color = Color(0xFF6e3b00))
                                .height(280.dp)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Oh Bang Ramen",
                                color = Color(0xFFfff700),
                                fontSize = 38.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.Serif
                            )
                            Row {
                                Column (
                                    modifier = Modifier.weight(1f)
                                ){
                                    Text(
                                        text = "Naga",
                                        color = Color(0xFFEDEFEE),
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = FontFamily.Serif
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Box {
                                        Text(
                                            text = "Experience the authentic taste of Japan with our exquisite dishes crafted to perfection.",
                                            color = Color.Black,
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily.SansSerif,
                                            modifier = Modifier
                                                .offset(x = 2.dp, y = 2.dp)
                                        )
                                        Text(
                                            text = "Experience the authentic taste of Japan with our exquisite dishes crafted to perfection.",
                                            color = Color(0xFFEDEFEE),
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily.SansSerif
                                        )
                                    }
                                }
                                Image(
                                    modifier = Modifier
                                        .height(140.dp)
                                        .width(130.dp)
                                        .clip(RoundedCornerShape(15.dp)),
                                    painter = painterResource(id = R.drawable.heroimage),
                                    contentDescription = "Hero Image",
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            OutlinedTextField(
                                value = searchPhrase,
                                onValueChange = {
                                    searchPhrase = it
                                },
                                placeholder = { Text(text = "Enter search phrase")},
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = Color(0xFFEDEFEE),
                                    focusedContainerColor = Color.White
                                ),
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        Text(
                            text = "See Our Menu",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.Start)
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            item {
                                CategoryButton(category = "Starters", selectedCategory = selectedCategory) { selectedCategory = it }
                            }
                            item {
                                CategoryButton(category = "Mains", selectedCategory = selectedCategory) { selectedCategory = it }
                            }
                            item {
                                CategoryButton(category = "Desserts", selectedCategory = selectedCategory) { selectedCategory = it }
                            }
//                            item {
//                                CategoryButton(category = "Drinks", selectedCategory = selectedCategory) { selectedCategory = it }
//                            }
                        }
                    }
                }
                items(filteredMenuItems) { menuItem ->
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            // Navigate to detail screen when clicked
                            navController.navigate("foodDetail/${menuItem.id}")
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // For broken Images.
                            val painter = if (menuItem.title == "Lemon Desert") {
                                painterResource(id = R.drawable.lemondessert)
                            } else if (menuItem.title == "Grilled Fish") {
                                painterResource(id = R.drawable.grilledfish)
                            } else {
                                rememberImagePainter(menuItem.image)
                            }
                            // For broken Images.
                            Image(
                                painter = painter,
                                contentDescription = "Hero Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )

                            Column(
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .weight(1f)
                            ) {
                                Text(
                                    text = menuItem.title,
                                    color = Color.Black,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = menuItem.description,
                                    color = Color(0xFF495E57),
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = "$${String.format("%.2f", menuItem.price.toFloat())}",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 17.sp,
                                    color = Color(0xFF495E57)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Display network error notification if necessary
    if (networkError) {
        // Replace with your preferred way to notify the user about network issues
        AlertDialog(
            onDismissRequest = { networkError = false },
            title = { Text("Ping Error") },
            text = { Text("Network request failed. Please check your internet connection.") },
            confirmButton = {
                Button(
                    onClick = { networkError = false },
                    //colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                ) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun CategoryButton(category: String, selectedCategory: String, onClick: (String) -> Unit) {
    val isSelected = selectedCategory == category
    val backgroundColor = if (isSelected) Color(0xFF495E57) else Color(0xFFEDEFEE)
    val textColor = if (isSelected) Color(0xFFEDEFEE) else Color(0xFF495E57)

    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .height(36.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick(if (isSelected) "" else category) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category,
            color = textColor,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(120.dp)
                    .width(180.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color(0xFF6e3b00))
                    .height(270.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Oh Bang Ramen",
                    color = Color(0xFFfff700),
                    fontSize = 38.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif
                )
                Row {
                    Column (
                        modifier = Modifier.weight(1f)
                    ){
                        Text(
                            text = "Naga",
                            color = Color(0xFFEDEFEE),
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Box {
                            Text(
                                text = "Experience the authentic taste of Japan with our exquisite dishes crafted to perfection.",
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = FontFamily.SansSerif,
                                modifier = Modifier
                                    .offset(x = 2.dp, y = 2.dp)
                            )
                            Text(
                                text = "Experience the authentic taste of Japan with our exquisite dishes crafted to perfection.",
                                color = Color(0xFFEDEFEE),
                                fontSize = 16.sp,
                                fontFamily = FontFamily.SansSerif
                            )
                        }
                    }
                    Image(
                        modifier = Modifier
                            .height(140.dp)
                            .width(130.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        painter = painterResource(id = R.drawable.heroimage),
                        contentDescription = "Hero Image",
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = "Enter search phrase")},
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White
                    ),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(start = 0.dp, end = 0.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Text(
                text = "See Our Menu",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Start)
            )
            Row(
                modifier = Modifier.padding(vertical = 0.dp)
            ) {
                CategoryButton(category = "Starters", selectedCategory = "") {}
                CategoryButton(category = "Mains", selectedCategory = "") {}
                CategoryButton(category = "Desserts", selectedCategory = "") {}
                CategoryButton(category = "Drinks", selectedCategory = "") {}
            }

            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.greeksalad),
                        contentDescription = "Hero Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "Premium Oh Bang Salad",
                            color = Color.Black,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Savor the luxury of our premium salad, crafted with the finest and freshest ingredients",
                            color = Color(0xFF495E57),
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "$10.00",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,
                            color = Color(0xFF495E57)
                        )
                    }
                }
            }
            ////////////////////////////////
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.lemondessert),
                        contentDescription = "Hero Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "Lemon Desert",
                            color = Color.Black,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Traditional homemade Italian Lemon Ricotta Cake.",
                            color = Color(0xFF495E57),
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "$10.00",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,
                            color = Color(0xFF495E57)
                        )
                    }
                }
            }
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.grilledfish),
                        contentDescription = "Hero Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "Grilled Fish",
                            color = Color.Black,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "The famous Greek salad of crispy lettuce, peppers, olives, our Chicago.",
                            color = Color(0xFF495E57),
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "$10.00",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,
                            color = Color(0xFF495E57)
                        )
                    }
                }
            }
        }
    }
}