package com.interstellar.rahulpihujetpackdemo.basicUI.segmentDemo

import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.interstellar.rahulpihujetpackdemo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleChoiceSegmenteDemo(){
    val choices = remember {
        mutableStateListOf("Good", "Better", "Best")
    }
    var selectedChoiceIndex = remember {
        mutableIntStateOf(0)
    }
    val myXmlColor = colorResource(id = R.color.blue_btn_bg_color)

    SingleChoiceSegmentedButtonRow {
        choices.forEachIndexed { index, choice ->

            SegmentedButton(
                selected = selectedChoiceIndex.intValue == index,
                onClick = { selectedChoiceIndex.intValue = index },
                colors = SegmentedButtonDefaults.colors(
                    activeBorderColor = myXmlColor,
                    activeContainerColor = myXmlColor,
                    activeContentColor = Color.Black,
                    inactiveContentColor = Color.White,
                    inactiveBorderColor = myXmlColor,
                    inactiveContainerColor = Color.Black
                ),
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = choices.count()
                )
            ) {
                Text(choice)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SingleChoiceSegmenteDemoPreview() {
    MaterialTheme {
        SingleChoiceSegmenteDemo()
    }
}