package com.interstellar.rahulpihujetpackdemo.basicUI

import android.widget.ScrollView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.presentation.theme.RahulPihuJetPackDemoTheme





@Composable
fun Demo3LazyColumnDemo() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.End, // Aligns all Texts to end
        contentPadding = PaddingValues(top = 50.dp) // for top padding like your Divider
    ) {
        item {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
            )
        }

        items(50) { index ->
            Text("Data ${index + 1}")
        }
    }
}

@Composable
fun Demo3ColumnDemo1() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(start = 30.dp, bottom = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
        )

        // Repeat Text items...
        repeat(50) {
            Text("Data ${it + 1}")
        }
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun Demo3ColumnDemoPreview() {
    RahulPihuJetPackDemoTheme {
        Demo3LazyColumnDemo()
    }
}