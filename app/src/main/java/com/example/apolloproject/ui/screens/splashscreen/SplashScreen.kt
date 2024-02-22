package com.example.apolloproject.ui.screens.splashscreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.Dp

import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import com.example.apolloproject.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val loginPath= stringResource(id = R.string.pantallaLogin)
    val scale = remember {
      Animatable(1.5f)
    }
    LaunchedEffect(key1 = true ){
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 500,
                easing ={
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            ) ,


        )
        delay(3000L)
        navController.navigate(loginPath)
    }
   Box(contentAlignment = Alignment.Center,
       modifier = Modifier
           .fillMaxSize()
           .background(Color.Black)
       
   ) {
       Image(painter = painterResource(id = R.drawable.elden), contentDescription ="" ,Modifier.scale(scale.value))
       ProgressBar(percentege = 1f , animDuration =3000 )

   }

}


@Composable
fun ProgressBar(
    percentege:Float,

    radius: Dp =100.dp,
    color:Color= Color.White,
    strokeWidth:Dp=8.dp,
    animDuration:Int,
    animDelay:Int=0
){
    var animPlay by remember {
        mutableStateOf(false)
    }
    val  currPer= animateFloatAsState(
        targetValue =if (animPlay) percentege else 0f ,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animPlay=true
    }

    Box(contentAlignment = Alignment.Center ,
        modifier = Modifier.size(radius*2f)
    ){
        Canvas(modifier = Modifier.size(radius*2f) ){
            drawArc(
                color = color,
                -90F,360*currPer.value,
                useCenter = false,
                style = Stroke(
                    strokeWidth.toPx(),cap=StrokeCap.Round
                )

            )
        }

    }




}