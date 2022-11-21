package com.haeseong.composetest

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.haeseong.composetest.ui.home.UserCard
import com.haeseong.composetest.ui.theme.ComposeTestTheme

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ComposeTestTheme {
                Navigation(navController = navController)
            }
        }
    }
}

@Composable
fun LoginScreen(navController: (String) -> Unit) {
    val context = LocalContext.current
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            )
            {
                Text(
                    text = "로그인창",
                    Modifier.padding(
                        horizontal = 100.dp,
                        vertical = 100.dp
                    ),
                    fontSize = 40.sp
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column() {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "아이디",
                            fontSize = 16.sp
                        )
                        OutlinedTextField(
                            value = id,
                            onValueChange = { id = it },
                            Modifier.padding(16.dp, 0.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.padding(0.dp, 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "비밀번호",
                            fontSize = 16.sp
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            maxLines = 1,
                            textStyle = TextStyle(
                                color = Color.Blue,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(16.dp, 0.dp)
                        )
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    var type = ""
                    type = if (id == "gkrlsanj" && password == "qlapdls2") {
                        ScreenType.TEST.name
                    } else {
                        ScreenType.NO_TYPE.name
                    }
                    try {
                        navController.invoke(type)
                    } catch (e: Exception) {
                        showToast(context, "아이디 비번을 확인해 주세요.")
                        e.printStackTrace()
                    }
                }) {
                    Text(text = "로그인 버튼")
                    Log.d("++Button Click", "id,pw : $id / $password")
                }
            }
        }
    }
}


fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

@Composable
fun TestScreen(navController: (String) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "TestScreen"
        )
        Button(onClick = {
            navController.invoke("login")
        }) {
        }
    }
}


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ScreenType.LOGIN.name
    ) {
        composable(ScreenType.LOGIN.name) {
            LoginScreen(navController = {
                navController.navigate(it)
                Log.d("++LoginScreen", it)
            })
        }
        composable(ScreenType.TEST.name) {
            TestScreen(navController = { navController.navigate(it) })
        }
        composable(ScreenType.HOME.name){
            UserCard()
        }
    }
}

enum class ScreenType() {
    LOGIN,
    HOME,
    TEST,
    NO_TYPE
}