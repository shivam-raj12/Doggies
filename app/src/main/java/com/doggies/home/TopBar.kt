package com.doggies.home

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.doggies.repository.Doggies

@Composable
fun TopBar(
    onFilterChange: (String?) -> Unit
) {
    var search by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current

    Column(
        Modifier
            .fillMaxWidth()
            .background(Color(31, 31, 31))
            .padding(top = 22.dp, start = 16.dp, end = 16.dp, bottom = 5.dp)
            .statusBarsPadding()
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Doggies",
            style = MaterialTheme.typography.headlineLarge,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = search,
                onValueChange = { search = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(232, 232, 232),
                    unfocusedBorderColor = Color(232, 232, 232),
                    focusedContainerColor = Color(232, 232, 232),
                    unfocusedContainerColor = Color(232, 232, 232)
                ),
                placeholder = {
                    Text(
                        text = "Search by Breed or Sub-Breed",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            )
            IconButton(
                modifier = Modifier
                    .background(
                        Color(30, 9, 9, 255),
                        RoundedCornerShape(8.dp)
                    )
                    .clip(
                        RoundedCornerShape(8.dp)
                    ),
                onClick = remember {
                    {
                        focusManager.clearFocus()
                    }
                }
            ) {
                Icon(Icons.Default.Search, null)
            }
        }
        FilterDog(search, onFilterChange)
    }
}

@Composable
fun CustomFilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = label,
        colors = FilterChipDefaults.filterChipColors(
            labelColor = Color(232, 232, 232)
        )
    )
}

@Composable
private fun FilterDog(
    search: String,
    onFilterChange: (String?) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var breedFilter by remember {
        mutableStateOf("")
    }
    var subBreedFilter by remember {
        mutableStateOf("")
    }
    val breedList = remember {
        mutableStateMapOf<String, List<String>>()
    }
    LaunchedEffect(Unit) {
        val breed = Doggies.getAllBreed()
        breedList.putAll(breed.breedList)
    }
    val filteredBreedList by remember(search) {
        derivedStateOf {
            breedList.filter { map ->
                map.key.startsWith(search, true) || map.value.any { it.startsWith(search, true) }
            }
        }
    }
    if (filteredBreedList.isNotEmpty()) {
        BreedFilterList(
            filter = breedFilter,
            onBreedFilterChange = remember {
                {
                    breedFilter = it ?: ""
                    subBreedFilter = ""
                    onFilterChange(it)
                    focusManager.clearFocus()
                }
            },
            breedList = filteredBreedList.keys.toList()
        )
        val subBreedList = remember(filteredBreedList, breedFilter) { breedList[breedFilter] }
        if (!subBreedList.isNullOrEmpty()) {
            if (subBreedList.size < 2) subBreedFilter = subBreedList.first()
            Log.d("TAG", "FilterDog: $breedFilter")
            SubBreedFilterList(
                subBreedFilter = subBreedFilter,
                onSubBreedFilterChange = remember {
                    {
                        subBreedFilter = it ?: ""
                        onFilterChange("$breedFilter${if (it!=null) "/${it}" else ""}")
                    }
                },
                subBreedList = subBreedList
            )
        }
    }
}