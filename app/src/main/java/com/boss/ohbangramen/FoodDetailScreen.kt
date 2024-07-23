package com.boss.ohbangramen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.boss.ohbangramen.R

@Composable
fun FoodDetailScreen(navController: NavHostController, viewModel: MenuViewModel, itemId: Int) {
    val menuItemList by viewModel.getMenuItemById(itemId).observeAsState(emptyList())
    val menuItem = menuItemList.firstOrNull()

    menuItem?.let {
        // Display item details
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val painter = if (menuItem.title == "Lemon Desert") {
                painterResource(id = R.drawable.lemondessert)
            } else if (menuItem.title == "Grilled Fish") {
                painterResource(id = R.drawable.grilledfish)
            } else {
                rememberImagePainter(menuItem.image)
            }
            Image(
                painter = painter,
                contentDescription = "Hero Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                text = menuItem.title,
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = menuItem.description,
                color = Color(0xFF495E57),
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "$${String.format("%.2f", menuItem.price.toFloat())}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color(0xFF495E57),
                modifier = Modifier.padding(top = 8.dp)
            )
            // Adding buttons for navigation and adding to order
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                colors = ButtonDefaults.buttonColors(Color(0xFFF4CE14)),
                onClick = {
                    // Show a message to the user
                    Toast.makeText(navController.context, "We currently don't offer delivery, but are considering it. Stay tuned!", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add to Order", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                colors = ButtonDefaults.buttonColors(Color(0xFFF4CE14)),
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Back", color = Color.Black)
            }
        }
    } ?: run {
        // Handling the case where the item is not found
        Text("Item not found", color = Color.Red, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}