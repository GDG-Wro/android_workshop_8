package pl.gdgwroclaw.workshop.hardware

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Button
import android.widget.Toast

class TouchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch)
        val gestureDetector =
            GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
                override fun onLongPress(e: MotionEvent?) {
                    showToast("long press")
                }

                override fun onDoubleTap(e: MotionEvent?): Boolean {
                    showToast("double tap")
                    return true
                }
            })
        val scaleGestureDetector =
            ScaleGestureDetector(this, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

                var initialScaleFactor = 1.0f

                override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
                    initialScaleFactor = detector.scaleFactor
                    return super.onScaleBegin(detector)
                }

                override fun onScaleEnd(detector: ScaleGestureDetector) {
                    showToast("scale ${detector.scaleFactor - initialScaleFactor}")
                }
            })

//        findViewById<View>(R.id.playground).setOnTouchListener { _, motionEvent ->
//            //gestureDetector.onTouchEvent(motionEvent) OR
//            scaleGestureDetector.onTouchEvent(motionEvent)
//        }

        findViewById<Button>(R.id.postButton).setOnClickListener {
            showToast("post")
        }
        findViewById<Button>(R.id.logoutButton).setOnClickListener {
            showToast("logout")
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}