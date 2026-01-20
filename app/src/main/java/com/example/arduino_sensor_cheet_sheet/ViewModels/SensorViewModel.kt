package com.example.arduino_sensor_cheet_sheet.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.arduino_sensor_cheet_sheet.DataClasses.SensorData
import com.example.arduino_sensor_cheet_sheet.DataClasses.fetchEnumStatus
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class SensorViewModel: ViewModel() {

    val baseUrl = "http://192.168.123.42:8000/"
    private val okHttpClient = OkHttpClient()

    private val _response_status = MutableStateFlow<fetchEnumStatus>(fetchEnumStatus.IDLE)
    val response_status: StateFlow<fetchEnumStatus> = _response_status

    private val _sensorData = MutableStateFlow<SensorData>(SensorData(listOf(), "", false))
    val responseData: StateFlow<SensorData> = _sensorData


    fun getAllSensor() {
        val request = Request.Builder()
            .url(baseUrl + "get_all_sensors")
            .build()
        _response_status.value = fetchEnumStatus.FETCHING


        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("FetchUsers_TAG", "Error $e")
                _response_status.value = fetchEnumStatus.FAILED


            }

            override fun onResponse(call: Call, response: Response) {
                val body: String? = response.body?.string()
                Log.i("FetchUsers_TAG", "Response $body")
                _response_status.value = fetchEnumStatus.SUCCESS
                val sensorData = Gson().fromJson(body, SensorData::class.java)
                _sensorData.value = sensorData

            }

        })
    }
}