package com.interstellar.rahulpihujetpackdemo.basicUI

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.ui.theme.RahulPihuJetPackDemoTheme


@Composable
fun SimpleCardExample() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 40.dp)
        ,
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Title", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Description goes here.")
        }
    }
}


@Composable
fun ElevatedCardExample() {

    Box( modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        ElevatedCard( elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier.size(width = 200.dp, height = 100.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White))
         {

             Text("This is Evaluated Card", modifier = Modifier.padding(16.dp),
                 textAlign = TextAlign.Unspecified)
        }
    }
}

@Composable
fun OutlinedCardExample() {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 40.dp)
        ,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.White // optional background
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Outlined Card",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "This card has a border and no shadow.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable
fun SimpleCardExamplePreview() {
    RahulPihuJetPackDemoTheme {
        SimpleCardExample()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable
fun ElevatedCardExamplePreview() {
    RahulPihuJetPackDemoTheme {
        ElevatedCardExample()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable
fun OutlineCardExamplePreview() {
    RahulPihuJetPackDemoTheme {
        OutlinedCardExample()
    }
}


