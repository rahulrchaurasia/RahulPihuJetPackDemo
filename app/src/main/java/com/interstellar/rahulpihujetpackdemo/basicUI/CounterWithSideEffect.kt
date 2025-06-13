package com.interstellar.rahulpihujetpackdemo.basicUI

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.provider.FontsContractCompat.Columns
import com.interstellar.rahulpihujetpackdemo.ui.theme.RahulPihuJetPackDemoTheme

@Composable
fun CounterWithoutSideEffect(){

    var count by remember { mutableIntStateOf(0) }

    Log.d("POLICYBOSS","Direct Print Count = $count")

    SideEffect {
        Log.d("POLICYBOSS","✅ SideEffect: Count = $count")
    }

    Column (horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()){

        Text("Count: $count",modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { count++ }) {
            Text("Increment")
        }

    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CounterWithoutSideEffectPreview() {
    RahulPihuJetPackDemoTheme {
        CounterWithoutSideEffect()
    }
}

