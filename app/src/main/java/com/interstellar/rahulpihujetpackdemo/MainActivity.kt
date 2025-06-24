package com.interstellar.rahulpihujetpackdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import com.interstellar.rahulpihujetpackdemo.presentation.theme.RahulPihuJetPackDemoTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint // ✅ Add this annotation
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appDataManager: AppDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize AuthManager with application context

       // ✅ Hilt injects AppDataManager

        enableEdgeToEdge()
        setContent {
            RahulPihuJetPackDemoTheme {


                val navController = rememberNavController()

                // Provide NavController to all composables
                CompositionLocalProvider(LocalNavController provides navController) {



                    RootNavGraph(
                        navController = navController,
                        appDataManager = appDataManager // // ✅ Pass to RootNavGraph
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