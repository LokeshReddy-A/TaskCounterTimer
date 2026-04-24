package com.example.taskcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import androidx.navigation.NavController
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "counter"
    ) {

        composable("counter") {
            TaskCounterApp(navController)
        }

        composable("timer") {
            TimerScreen(navController)
        }
    }
}

@Composable
fun TaskCounterApp(navController: NavController) {

    var count by remember { mutableStateOf(0) }
    var isDark by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = if (isDark) darkColorScheme() else lightColorScheme()
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Tasks Completed",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "$count",
                    style = MaterialTheme.typography.displayLarge
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row {

                    Button(onClick = { count++ }) {
                        Text("+")
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(onClick = {
                        if (count > 0) count--
                    }) {
                        Text("-")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = { count = 0 }) {
                    Text("Reset")
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text("Dark Mode")

                    Spacer(modifier = Modifier.width(10.dp))

                    Switch(
                        checked = isDark,
                        onCheckedChange = { isDark = it }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = {
                    navController.navigate("timer")
                }) {
                    Text("Go to Timer")
                }
            }
        }
    }
}

@Composable
fun TimerScreen(navController: NavController) {

    var time by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }


    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000)
            time++
        }
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Timer", style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(20.dp))

                Text("$time sec", style = MaterialTheme.typography.displayLarge)

                Spacer(modifier = Modifier.height(20.dp))

                Row {

                    Button(onClick = { isRunning = true }) {
                        Text("Start")
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(onClick = { isRunning = false }) {
                        Text("Stop")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = { time = 0 }) {
                    Text("Reset")
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text("Back to Counter")
                }
            }
        }
    }
}