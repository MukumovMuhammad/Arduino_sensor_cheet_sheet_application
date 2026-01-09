package com.example.arduino_sensor_cheet_sheet

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.arduino_sensor_cheet_sheet.ui.theme.Arduino_Sensor_cheet_SheetTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val is_drawerOpen = mutableStateOf(false)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Arduino_Sensor_cheet_SheetTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()


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
                                title = { Text("Arduino Docs") },
                                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                                    }
                                }
                            )
                        }
                    ) { paddingValues ->
                        Box(modifier = Modifier.padding(paddingValues)) {
                            Text("Content")
                        }
                    }
                }
            }
        }
    }
}