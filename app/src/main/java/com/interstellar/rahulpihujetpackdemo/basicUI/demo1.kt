package com.interstellar.rahulpihujetpackdemo.basicUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.interstellar.rahulpihujetpackdemo.Greeting
import com.interstellar.rahulpihujetpackdemo.R
import com.interstellar.rahulpihujetpackdemo.presentation.theme.RahulPihuJetPackDemoTheme


@Composable
fun Demo1AcceessStringResource(){

    Box(modifier = Modifier
          .fillMaxSize()
        .padding(horizontal = 50.dp)
        .padding(vertical = 200.dp)

        .background(color =  Color.Red),
        contentAlignment = Alignment.Center

    ){
        Text(text = stringResource(id = R.string.app_name),
            modifier = Modifier
                     .fillMaxSize()
                      .height(200.dp)
                      .padding(start = 10.dp,end = 5.dp, top = 10.dp, bottom = 10.dp)
                     ,
            color = colorResource(R.color.Orange) ,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic

            )

    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun Demo1AcceessStringResourcePreview() {
    RahulPihuJetPackDemoTheme {
        Demo1AcceessStringResource()
    }
}


