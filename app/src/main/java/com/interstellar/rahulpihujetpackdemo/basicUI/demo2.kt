package com.interstellar.rahulpihujetpackdemo.basicUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.R
import com.interstellar.rahulpihujetpackdemo.presentation.theme.RahulPihuJetPackDemoTheme


@Composable
fun Demo2AccessImage() {
    Box(
        modifier = Modifier.fillMaxSize()
                  .padding(top = 50.dp), // space from top
        contentAlignment = Alignment.TopStart
    ) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 10.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Car Information" ,
                modifier =  Modifier.padding(start = 16.dp)

            )

            Image(
                painter = painterResource(id = R.drawable.car1),
                contentDescription = "carImage",
                modifier = Modifier

                    .height(200.dp)
                    .width(200.dp),

                contentScale = ContentScale.Fit

            )

        }


    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun Demo2AcceessImagePreview() {
    RahulPihuJetPackDemoTheme {
        Demo2AccessImage()
    }
}