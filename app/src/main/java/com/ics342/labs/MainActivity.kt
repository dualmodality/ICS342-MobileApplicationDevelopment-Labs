package com.ics342.labs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ics342.labs.data.DataItem
import com.ics342.labs.ui.theme.LabsTheme

private val dataItems = listOf(
    DataItem(1, "Item 1", "Description 1"),
    DataItem(2, "Item 2", "Description 2"),
    DataItem(3, "Item 3", "Description 3"),
    DataItem(4, "Item 4", "Description 4"),
    DataItem(5, "Item 5", "Description 5"),
    DataItem(6, "Item 6", "Description 6"),
    DataItem(7, "Item 7", "Description 7"),
    DataItem(8, "Item 8", "Description 8"),
    DataItem(9, "Item 9", "Description 9"),
    DataItem(10, "Item 10", "Description 10"),
    DataItem(11, "Item 11", "Description 11"),
    DataItem(12, "Item 12", "Description 12"),
    DataItem(13, "Item 13", "Description 13"),
    DataItem(14, "Item 14", "Description 14"),
    DataItem(15, "Item 15", "Description 15"),
    DataItem(16, "Item 16", "Description 16"),
    DataItem(17, "Item 17", "Description 17"),
    DataItem(18, "Item 18", "Description 18"),
    DataItem(19, "Item 19", "Description 19"),
    DataItem(20, "Item 20", "Description 20"),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "DataItemList") {
                composable(route = "DataItemList") {
                    DataItemList(dataItems = dataItems, navController = navController)
                }

                composable(route = "DetailsScreen/{id}",
                    arguments = listOf(navArgument("id") {
                        type = NavType.IntType
                        defaultValue = 0
                    })
                ) {entry ->
                    DetailScreenView(dataItem = dataItems[entry.arguments!!.getInt("id") - 1])
                }
            }
        }
    }
}


@Composable
fun DataItemView(dataItem: DataItem, navController: NavController) {
    Row(modifier = Modifier
        .fillMaxSize()
        .clickable(onClick = {
            navController.navigate(route = buildString {
                append("DetailsScreen/")
                append(dataItem.id)
            })
        })) {
        Text(text = dataItem.id.toString(),
                style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.size(16.dp))
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = dataItem.name,
                    style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = dataItem.description,
                    style = MaterialTheme.typography.bodySmall)
            
        }
    }
}

@Composable
fun DetailScreenView(dataItem: DataItem) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = dataItem.id.toString(),
                style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = dataItem.name,
                style = MaterialTheme.typography.headlineLarge)
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = dataItem.description,
            style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun DataItemList(dataItems: List<DataItem>, navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = dataItems) { DataItemView(dataItem = it, navController = navController) }
    }

}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreenView(dataItem = dataItems[0])

}
