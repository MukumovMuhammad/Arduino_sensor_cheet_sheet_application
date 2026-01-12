package com.example.arduino_sensor_cheet_sheet

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.arduino_sensor_cheet_sheet.DataClasses.Item
import com.example.arduino_sensor_cheet_sheet.DataClasses.fetchEnumStatus
import com.example.arduino_sensor_cheet_sheet.ViewModels.SensorViewModel
import com.example.arduino_sensor_cheet_sheet.ui.theme.Arduino_Sensor_cheet_SheetTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val is_drawerOpen = mutableStateOf(false)
    private val sensorViewModel: SensorViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Arduino_Sensor_cheet_SheetTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                var isSearching by remember { mutableStateOf(false) }
                var searchQuery by remember { mutableStateOf("") }

                val response_status by sensorViewModel.response_status.collectAsState()
                val sensorData by sensorViewModel.responseData.collectAsState()

                val pullToRefreshState = rememberPullToRefreshState()
                var isLoading by remember { mutableStateOf(false) }


                LaunchedEffect(isLoading) {
                    if (isLoading) {
                        delay(1000)
                        isLoading = false
                    }
                }
                LaunchedEffect(Unit) {
                    sensorViewModel.getAllSensor()
                }
                ModalNavigationDrawer(

                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet (
                            modifier = Modifier.fillMaxWidth(0.7f)
                        ){
                            Text("Arduino Docs", modifier = Modifier.padding(16.dp))
                            HorizontalDivider()
                            NavigationDrawerItem(
                                label = {Text("Add sensor") },
                                selected = false,
                                onClick = {
                                    Toast.makeText(this@MainActivity, "Currently not working", Toast.LENGTH_SHORT).show()
                                }
                            )
                            NavigationDrawerItem(
                                label = {Text("About app") },
                                selected = false,
                                onClick = {
                                    Toast.makeText(this@MainActivity, "Currently not working", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                title = {
                                    if (isSearching) {
                                        TextField(
                                            value = searchQuery,
                                            onValueChange = { searchQuery = it },
                                            placeholder = { Text("Search Arduino docs…") },
                                            singleLine = true,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    } else {
                                        Text("Arduino Docs")
                                    }
                                },
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(
                                            Icons.Default.Menu,
                                            contentDescription = "Menu",
                                            tint = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(onClick = {
                                        if (isSearching) searchQuery = ""
                                        isSearching = !isSearching
                                    }) {
                                        Icon(
                                            if (isSearching) Icons.Default.Close else Icons.Default.Search,
                                            contentDescription = "Search",
                                            tint = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }
                            )
                        }

                    ) { paddingValues ->

                        PullToRefreshBox(
                            modifier = Modifier.fillMaxSize().padding(paddingValues),
                            state = pullToRefreshState,
                            isRefreshing = isLoading,
                            onRefresh = {
                                isLoading = true
                                sensorViewModel.getAllSensor()
                            }
                        ) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                when(response_status){
                                    fetchEnumStatus.IDLE -> {}
                                    fetchEnumStatus.FETCHING -> {
                                        item {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(top = 40.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                CircularProgressIndicator()
                                            }
                                        }
                                    }
                                    fetchEnumStatus.FAILED -> {
                                       item{
                                           Text("Failed", color= Color.Red)
                                       }
                                    }
                                    fetchEnumStatus.SUCCESS -> {

                                        items(sensorData.items) {
                                            SensorCard(it)
                                        }

                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}



@Composable
fun SensorCard(item: Item) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 1. Top Image Section (placeholder or resource)
            AsyncImage(
                model = SensorViewModel().baseUrl + item.image_path,
                contentDescription = null,
                modifier = Modifier
                    .weight(1f) // Takes up remaining space
                    .fillMaxWidth()
                    .background(Color.White)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            // 2. Bottom Text Section (Fully covered in Arduino Color)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
