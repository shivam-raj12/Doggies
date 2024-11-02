package com.doggies.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

@Composable
fun SubBreedFilterList(
    subBreedFilter: String,
    onSubBreedFilterChange: (String?) -> Unit,
    subBreedList: List<String>
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        if (subBreedList.size >= 2) {
            item {
                CustomFilterChip(
                    selected = subBreedFilter.isBlank(),
                    onClick = {
                        if (subBreedFilter.isNotBlank()) {
                            onSubBreedFilterChange(null)
                        }
                    },
                    label = {
                        Text("All")
                    }
                )
            }
        }
        items(subBreedList) {
            CustomFilterChip(
                selected = subBreedFilter == it,
                onClick = remember {
                    {
                        if (subBreedFilter != it) {
                            onSubBreedFilterChange(it)
                        }
                    }
                },
                label = {
                    Text(it)
                }
            )
        }
    }
}