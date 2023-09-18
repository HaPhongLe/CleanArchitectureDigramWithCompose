package com.example.customcomposecomponent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customcomposecomponent.ui.theme.CustomComposeComponentTheme
import com.example.customcomposecomponent.ui.theme.lightBlue
import com.example.customcomposecomponent.ui.theme.lightGreen
import com.example.customcomposecomponent.ui.theme.lightPink
import com.example.customcomposecomponent.ui.theme.lightYellow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomComposeComponentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    DrawArchitecture()

                }
            }
        }
    }
}

@Composable
fun FlowRow (
    modifier: Modifier = Modifier.verticalScroll(state = rememberScrollState()),
    content: @Composable ()-> Unit
){
    Layout(
        modifier = modifier,
        measurePolicy = {measurables, constraints ->
            val placeables = measurables.map {
                it.measure(constraints)
            }

            val groupedPlaceables = mutableListOf<List<Placeable>>()
            var currentGroup = mutableListOf<Placeable>()
            var currentGroupWidth = 0
            placeables.forEach { placeable ->
                if(currentGroupWidth + placeable.width <= constraints.maxWidth){
                    currentGroup.add(placeable)
                    currentGroupWidth += placeable.width
                }else{
                    groupedPlaceables.add(currentGroup)
                    currentGroup = mutableListOf(placeable)
                    currentGroupWidth = placeable.width
                }
            }

            if (currentGroup.isNotEmpty()){
                groupedPlaceables.add(currentGroup)
            }

            layout(
                width = constraints.maxWidth,
                height = constraints.maxHeight
            ){
                var  yPosition = 0
                groupedPlaceables.forEach{row ->
                    var xPosition = 0
                    row.forEach{placeable ->
                        placeable.place(
                            x = xPosition,
                            y = yPosition
                        )
                        xPosition += placeable.width
                    }
                    yPosition += row.maxOfOrNull { it.height }?: 0
                }

            }

        },
        content = content)
}


@OptIn(ExperimentalTextApi::class)
@Preview
@Composable
private fun DrawArchitecture() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(36.dp),
        contentAlignment = Alignment.Center
    ) {
        val textMeasurer = rememberTextMeasurer()

        val baseCircleRadius = 150f

        val textUI = stringResource(R.string.ui)
        val textControllers = stringResource(R.string.controllers)
        val textUseCases = stringResource(R.string.use_cases)
        val textEntities = stringResource(R.string.entities)
        val textWeb = stringResource(R.string.web)
        val textDb = stringResource(R.string.db)
        val textDevices = stringResource(R.string.devices)
        val textPresenters = stringResource(R.string.presenters)
        val textGateWays = stringResource(R.string.gateways)

        val style = TextStyle(
            fontSize = 16.sp,
            color = Color.Black,
        )

        val textLayoutResultOuterMost = remember(textUI) {
            textMeasurer.measure(textUI, style)
        }
        val textLayoutResultSecondLayer = remember(textControllers) {
            textMeasurer.measure(textControllers , style)
        }

        val textLayoutResultThirdLayer = remember(textUseCases) {
            textMeasurer.measure(textUseCases , style)
        }
        val textLayoutResulInnerMost = remember(textEntities) {
            textMeasurer.measure(textEntities , style)
        }


        Canvas(modifier = Modifier.fillMaxSize()) {

            /**
             * Outer layer
             */
            val firstCircleRadiusDiff = 3 * baseCircleRadius
            drawCircle(
                center = Offset(
                    x = center.x,
                    y = center.y
                ),
                radius = baseCircleRadius + firstCircleRadiusDiff,
                color = lightBlue,
                style = Fill
            )
            rotate(45f){
                drawText(
                    textMeasurer = textMeasurer,
                    text = textWeb,
                    style = style,
                    topLeft = Offset(
                        x = center.x - textLayoutResultOuterMost.size.width / 2,
                        y = center.y - textLayoutResultOuterMost.size.height / 2 - firstCircleRadiusDiff - baseCircleRadius/2,
                    ),
                )
            }
            rotate(315f){
                drawText(
                    textMeasurer = textMeasurer,
                    text = textUI,
                    style = style,
                    topLeft = Offset(
                        x = center.x - textLayoutResultOuterMost.size.width / 2,
                        y = center.y - textLayoutResultOuterMost.size.height / 2 + firstCircleRadiusDiff + baseCircleRadius/2,
                    ),
                )
            }
            rotate(45f){
                drawText(
                    textMeasurer = textMeasurer,
                    text = textDb,
                    style = style,
                    topLeft = Offset(
                        x = center.x - textLayoutResultOuterMost.size.width / 2,
                        y = center.y - textLayoutResultOuterMost.size.height / 2 + firstCircleRadiusDiff + baseCircleRadius/2,
                    ),
                )
            }
            rotate(315f){
                drawText(
                    textMeasurer = textMeasurer,
                    text = textDevices,
                    style = style,
                    topLeft = Offset(
                        x = center.x - textLayoutResultOuterMost.size.width / 2 -70f,
                        y = center.y - textLayoutResultOuterMost.size.height / 2 - firstCircleRadiusDiff - baseCircleRadius/2,
                    ),
                )
            }
            /**
             * Second Layer(Controllers, Presenters, Gateways)
             */
            val secondCircleRadiusDiff = baseCircleRadius * 2
            drawCircle(
                center = Offset(
                    x = center.x,
                    y = center.y
                ),
                radius = baseCircleRadius + secondCircleRadiusDiff,
                color = lightGreen,
                style = Fill
            )
            drawText(
                textMeasurer = textMeasurer,
                text = textControllers,
                style = style,
                topLeft = Offset(
                    x = center.x - textLayoutResultSecondLayer.size.width / 2,
                    y = center.y - textLayoutResultSecondLayer.size.height / 2 - secondCircleRadiusDiff - baseCircleRadius/2,
                )
            )
            rotate(295f){
                drawText(
                    textMeasurer = textMeasurer,
                    text = textPresenters,
                    style = style,
                    topLeft = Offset(
                        x = center.x - textLayoutResultSecondLayer.size.width / 2,
                        y = center.y - textLayoutResultSecondLayer.size.height / 2 + secondCircleRadiusDiff + baseCircleRadius/2,
                    )
                )
            }

            rotate(60f){
                drawText(
                    textMeasurer = textMeasurer,
                    text = textGateWays,
                    style = style,
                    topLeft = Offset(
                        x = center.x - textLayoutResultSecondLayer.size.width / 2,
                        y = center.y - textLayoutResultSecondLayer.size.height / 2 + secondCircleRadiusDiff + baseCircleRadius/2,
                    )
                )
            }
            /**
             * Third layer (Use cases)
             */
            val thirdCircleRadiusDiff = baseCircleRadius
            drawCircle(
                center = Offset(
                    x = center.x,
                    y = center.y
                ),
                radius = baseCircleRadius + thirdCircleRadiusDiff,
                color = lightPink,
                style = Fill
            )
            drawText(
                textMeasurer = textMeasurer,
                text = textUseCases,
                style = style,
                topLeft = Offset(
                    x = center.x - textLayoutResultThirdLayer.size.width / 2,
                    y = center.y - textLayoutResultThirdLayer.size.height / 2 - thirdCircleRadiusDiff - baseCircleRadius/2,
                )
            )
            /**
             * Core layer (Entities)
             */
            drawCircle(
                center = Offset(
                    x = center.x,
                    y = center.y
                ),
                radius = baseCircleRadius,
                color = lightYellow,
                style = Fill
            )
            drawText(
                textMeasurer = textMeasurer,
                text = textEntities,
                style = style,
                topLeft = Offset(
                    x = center.x - textLayoutResulInnerMost.size.width / 2,
                    y = center.y - textLayoutResulInnerMost.size.height / 2 ,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustomComposeComponentTheme {
//       MyCanvas()
    }
}