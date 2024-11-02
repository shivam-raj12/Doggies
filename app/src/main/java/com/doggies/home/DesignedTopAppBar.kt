package com.doggies.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun DesignedTopAppBar(
    modifier: Modifier,
    onFilterChange: (String?) -> Unit,
    snackBarHost: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val path = remember {
        Path()
    }

    CompositionLocalProvider(LocalContentColor provides Color(232, 232, 232)) {
        Column(
            modifier = modifier
        ) {
            TopBar(onFilterChange)
            Box {
                content()
                Canvas(
                    Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                ) {
                    path.apply {
                        moveTo(0f, size.height)
                        cubicTo(
                            x1 = size.width * 0.25f,
                            y1 = 0f,
                            x2 = size.width * 0.75f,
                            y2 = 100f,
                            x3 = size.width,
                            y3 = 0f
                        )
                        lineTo(0f, 0f)
                        lineTo(0f, size.height)
                        close()
                    }
                    drawPath(
                        path = path,
                        color = Color(31, 31, 31)
                    )
                }
            }
        }
    }
    Box(modifier, contentAlignment = Alignment.BottomCenter) {
        snackBarHost()
    }
}