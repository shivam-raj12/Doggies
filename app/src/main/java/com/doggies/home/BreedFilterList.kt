package com.doggies.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

@Composable
fun BreedFilterList(
    filter: String,
    onBreedFilterChange: (String?) -> Unit,
    breedList: List<String>
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            CustomFilterChip(
                selected = filter.isBlank(),
                onClick = {
                        if (filter.isNotBlank()) {
                            onBreedFilterChange(null)
                        }
                          }
                ,
                label = {
                    Text("All")
                }
            )
        }
        items(breedList){
            CustomFilterChip(
                selected = filter == it,
                onClick = {
                    if (filter != it) {
                        onBreedFilterChange(it)
                    }
                },
                label = {
                    Text(it)
                }
            )
        }
    }
}