package com.boss.ohbangramen

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.boss.ohbangramen.R

@Composable
fun Profile(navController: NavHostController, sharedPreferences: SharedPreferences) {

    val firstName = sharedPreferences.getString("first_name", "N/A")
    val lastName = sharedPreferences.getString("last_name", "N/A")
    val email = sharedPreferences.getString("email", "N/A")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(120.dp)
                .width(200.dp)
        )
        Spacer(modifier = Modifier.height(0.dp))

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Profile information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(60.dp))
            Text(text = "First name")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(text = firstName ?: "")
            }
            Spacer(modifier = Modifier.height(25.dp))
            Text(text = "Last name")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(text = lastName ?: "")
            }
            Spacer(modifier = Modifier.height(25.dp))
            Text(text = "Email")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(text = email ?: "")
            }
            Spacer(modifier = Modifier.weight(1f)) // Spacer to push content to the top
            Button(
                colors = ButtonDefaults.buttonColors(Color(0xFFF4CE14)),
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Go Back", color = Color.Black, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                colors = ButtonDefaults.buttonColors(Color(0xFFF4CE14)),
                onClick = {
                    sharedPreferences.edit().clear().apply()
                    navController.navigate("onboarding")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Log out", color = Color.Black, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(30.dp))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("preview_prefs", Context.MODE_PRIVATE)
    Profile(navController, sharedPreferences)
}