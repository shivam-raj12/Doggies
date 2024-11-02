package com.doggies.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.doggies.R
import kotlin.random.Random

@Composable
fun DogCard(imageUri:String) {
    val image = rememberAsyncImagePainter(imageUri)
    when(image.state.collectAsState().value){
        is AsyncImagePainter.State.Success -> {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(350.dp)
            )
        }
        is AsyncImagePainter.State.Empty,
        is AsyncImagePainter.State.Loading -> {
            Box(
                modifier = Modifier
                    .height(350.dp)
                    .background(Color(Random.nextLong(0xFFFFFFFF)))
            )
        }

        is AsyncImagePainter.State.Error -> {
            Image(
                painter = painterResource(R.drawable.error),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(350.dp)
            )
        }
    }
}