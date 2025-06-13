package com.interstellar.rahulpihujetpackdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.ParentGraph.RootNavGraph
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AppDataManager
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.LocalNavController
import com.interstellar.rahulpihujetpackdemo.rootGraph.router.AuthRoutes
import com.interstellar.rahulpihujetpackdemo.rootGraph.router.HomeRoutes
import com.interstellar.rahulpihujetpackdemo.ui.theme.RahulPihuJetPackDemoTheme

class MainActivity : ComponentActivity() {

    private lateinit var authManager: AppDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize AuthManager with application context
        authManager = AppDataManager(applicationContext)

        enableEdgeToEdge()
        setContent {
            RahulPihuJetPackDemoTheme {

//                    Surface(
//                        modifier = Modifier.fillMaxSize(),
//                        color = MaterialTheme.colorScheme.background
//                    ) {
//                        MainApp()
//                    }

                val navController = rememberNavController()

                // Provide NavController to all composables
                CompositionLocalProvider(LocalNavController provides navController) {

                    // Handle back press behavior
                    BackHandler(enabled = true) {
                        val currentRoute = navController.currentBackStackEntry?.destination?.route
                        when (currentRoute) {
                            HomeRoutes.Main.route -> {
                                // Close app if on main home screen
                                finish()
                            }
                            AuthRoutes.Login.route -> {
                                // Close app if on login screen
                                finish()
                            }
                            AuthRoutes.Welcome.route -> {
                                // Close app if on welcome screen
                                finish()
                            }
                            else -> {
                                navController.popBackStack()
                            }
                        }
                    }

                    RootNavGraph(
                        navController = navController,
                        authManager = authManager
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Column(modifier = modifier.padding(horizontal = 16.dp)
             .padding(vertical = 16.dp)
            .padding(top = 40.dp)
    ) {

        Text(
            text = "Hello $name!",
            modifier = modifier
        )

        Text(
            text = "Data is coming",
            modifier = modifier
        )
    }

    modifier.padding(horizontal = 16.dp)
    modifier.padding(vertical = 40.dp)

}

@Preview(
    showBackground = true,
    showSystemUi = true
     )
@Composable
fun GreetingPreview() {
    RahulPihuJetPackDemoTheme {
        Greeting("Android")
    }
}