package pl.gdgwroclaw.workshop.hardware

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow
import kotlin.math.sqrt

class SensorActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(SensorManager::class.java)!!
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    override fun onAccuracyChanged(sensor: Sensor, accurracy: Int) = Unit

    override fun onSensorChanged(event: SensorEvent) {
        val aX = event.values[0]
        val aY = event.values[1]
        val aZ = event.values[2]

        val a = sqrt(aX.pow(2) + aY.pow(2) + aZ.pow(2))
        if (a > 16f) {
            println("shake")
        }
    }
}