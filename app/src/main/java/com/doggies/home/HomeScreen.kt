package com.doggies.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.doggies.repository.Doggies
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    isDataLoaded: (Boolean) -> Unit
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    var isImagesEmpty by remember {
        mutableStateOf(false)
    }
    val dogList = remember {
        mutableStateListOf<String>()
    }
    var filter by remember {
        mutableStateOf<String?>(null)
    }
    val coroutineScope = rememberCoroutineScope()
    val state = rememberLazyGridState()
    LaunchedEffect(state) {
        snapshotFlow {
            state.layoutInfo.visibleItemsInfo.lastOrNull()?.index == state.layoutInfo.totalItemsCount - 1
        }.collect {
            if (!isImagesEmpty) {
                val dog = filter?.let {
                    Doggies.getDogsByBreed(it)
                } ?: Doggies.getAllDogs()
                dogList.addAll(dog.dogList)
                if (dog.dogList.size < 20) {
                    isImagesEmpty = true
                }
                isDataLoaded(true)
                if (dog.status.startsWith("Error", true)) {
                    snackBarHostState.showSnackbar(dog.status.replace("Error: ", ""))
                }
            }
        }
    }
    DesignedTopAppBar(
        snackBarHost = {
            SnackbarHost(snackBarHostState)
        },
        modifier = Modifier.fillMaxSize(),
        onFilterChange = {
            filter = it
            coroutineScope.launch {
                dogList.clear()
                state.scrollToItem(0)
                val dog = if (it == null) {
                    Doggies.getAllDogs()
                } else {
                    Doggies.getDogsByBreed(it)
                }
                dogList.addAll(dog.dogList)
                if (dog.dogList.size < 20) {
                    isImagesEmpty = true
                }
                if (dog.status.startsWith("Error", true)) {
                    snackBarHostState.showSnackbar(dog.status.replace("Error: ", ""))
                }
            }
        }
    ) {
        if (dogList.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier.fillMaxSize(),
                state = state,
                contentPadding = PaddingValues(top = 30.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    dogList.toSet().toList(),
                    key = {
                        it
                    }
                ) {
                    DogCard(it)
                }
                if (!isImagesEmpty) {
                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .navigationBarsPadding(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        } else {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}