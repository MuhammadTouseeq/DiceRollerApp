package com.smartdev.dicerollerapp

import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smartdev.dicerollerapp.ui.theme.DiceRollerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   DiceRollerApp()
                }
            }
        }
    }
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier
) {
    var isRotated by rememberSaveable { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (isRotated) 360F else 0f,
        animationSpec = tween(durationMillis = 800)

    )
    val animatedSizeDp: Dp by animateDpAsState(targetValue = if (isRotated) 350.dp else 200.dp,
        animationSpec = tween(durationMillis = 1000)
        )

    var result by remember { mutableStateOf(1) }
    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    val colorBackground = when (result) {
        1 -> Color.Black
        2 -> Color.Cyan
        3 -> Color.Magenta
        4 -> Color.Gray
        5 -> Color.Blue
        else -> Color.DarkGray
    }
    Box(
        Modifier.background(color = colorBackground)
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = imageResource),
                modifier = Modifier.rotate(rotationAngle).size(animatedSizeDp),

                contentDescription = "1"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(0.7f)
                    .fillMaxHeight(0.1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red,
                contentColor = Color.White
                    ),
                onClick = {
                    isRotated = !isRotated
                    Handler().postDelayed(Runnable {
                        result = (1..6).random()
                    }, 1000)


                }) {
                Text(text = stringResource(id = R.string.roll))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiceRollerApp() {

    DiceWithButtonAndImage(modifier =
    Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}