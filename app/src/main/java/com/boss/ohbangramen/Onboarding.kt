package com.boss.ohbangramen

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.boss.ohbangramen.R

@Composable
fun Onboarding(navController: NavHostController, sharedPreferences: SharedPreferences) {

    val firstNameState = remember { mutableStateOf("") }
    val lastNameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }

    var showAlertDialog = remember { mutableStateOf(false) }
    var showSuccessDialog = remember { mutableStateOf(false) }
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

    val focusManager = LocalFocusManager.current

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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(120.dp)
                        .width(200.dp)
                )
                Spacer(modifier = Modifier.height(0.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(color = Color(0xFF6e3b00))
                        .fillMaxWidth()
                        .padding(30.dp)
                ) {
                    Text(
                        fontSize = 22.sp,
                        color = Color.White,
                        text = "Welcome!",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        fontSize = 18.sp,
                        color = Color.White,
                        text = "いらっしゃいませ！"
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Let's get to know you!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    Text(text = "First name")
                    TextField(
                        value = firstNameState.value,
                        onValueChange = { newValue -> firstNameState.value = newValue },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(text = "Last name")
                    TextField(
                        value = lastNameState.value,
                        onValueChange = { newValue -> lastNameState.value = newValue },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(text = "Email")
                    TextField(
                        value = emailState.value,
                        onValueChange = { newValue -> emailState.value = newValue },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.weight(1f)) // Spacer to push content to the top
                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        colors = ButtonDefaults.buttonColors(Color(0xFFF4CE14)),
                        onClick = {
                            if (firstNameState.value.isBlank() || lastNameState.value.isBlank() || emailState.value.isBlank() || !emailRegex.matches(
                                    emailState.value
                                )
                            ) {
                                showAlertDialog.value = true
                            } else {
                                sharedPreferences.edit().apply() {
                                    putString("first_name", firstNameState.value)
                                    putString("last_name", lastNameState.value)
                                    putString("email", emailState.value)
                                    putBoolean("user_registered", true)
                                    apply()
                                }
                                showSuccessDialog.value = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Register", color = Color.Black, fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }

    if (showAlertDialog.value) {
        AlertDialog(
            onDismissRequest = { showAlertDialog.value = false },
            confirmButton = { Button(onClick = { showAlertDialog.value = false }) {
                Text(text = "OK")
            }
            },
            title = { Text(text = "Registration unsuccessful. Valid input is required.")},
            text = { Text(text = "") }
        )
    }

    if (showSuccessDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog.value = false
                navController.navigate("home")
            },
            confirmButton = { Button(onClick = {
                showSuccessDialog.value = false
                navController.navigate("home")
            }) {
                Text(text = "OK")
            } },
            title = { Text(text = "Registration successful!")},
            text = { Text(text = "") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("preview_prefs", Context.MODE_PRIVATE)
    Onboarding(navController, sharedPreferences)
}