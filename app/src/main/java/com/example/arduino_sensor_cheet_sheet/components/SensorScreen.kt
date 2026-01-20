package com.example.arduino_sensor_cheet_sheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil3.compose.AsyncImage
import com.example.arduino_sensor_cheet_sheet.ViewModels.SensorViewModel
import com.example.arduino_sensor_cheet_sheet.room.local.SensorEntity
import io.github.kbiakov.codeview.CodeView


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SensorScreen(item: SensorEntity, onBack: () -> Unit){

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(item.title!!)
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() })
                    {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            item {

                // 🔹 Header Image (Primary Surface)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    AsyncImage(
                        model = SensorViewModel().baseUrl + item.title_img,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        contentScale = ContentScale.Crop
                    )
                }

                // 🔹 Title
                Text(
                    text = item.title.orEmpty(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 16.dp)
                )

                // 🔹 Description
                Text(
                    text = item.context.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // 🔹 Wiring Diagram Section
            item {
                SectionTitle(
                    text = "Wiring Diagram",
                    color = MaterialTheme.colorScheme.primary
                )

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    AsyncImage(
                        model = SensorViewModel().baseUrl + item.scheme_img,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .aspectRatio(1f),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            // 🔹 Code Section
            item {
                SectionTitle(
                    text = "Arduino Code",
                    color = MaterialTheme.colorScheme.secondary
                )

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                    )
                ) {
                    ArduinoCodeView(code = item.code.orEmpty())
                }
            }
        }


    }
}

@Composable
fun ArduinoCodeView(code: String) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        factory = { context ->
           CodeView(context).apply {
               setCode(code)
           }
        }
    )
}


@Composable
fun SectionTitle(
    text: String,
    color: Color
) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = color,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}
