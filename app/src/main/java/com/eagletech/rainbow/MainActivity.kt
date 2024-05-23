package com.eagletech.rainbow

import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.eagletech.rainbow.customview.RainBowView.Companion.brush
import com.eagletech.rainbow.customview.RainBowView.Companion.colors
import com.eagletech.rainbow.customview.RainBowView.Companion.paths
import com.eagletech.rainbow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        var path = Path()
        var paint = Paint()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClick()
    }

    private fun colorCurrent(color: Int) {
        brush = color
        path = Path()
    }

    private fun setClick() {
        binding.colorRed.setOnClickListener {
            Toast.makeText(this, "Red", Toast.LENGTH_LONG).show()
            val color = ContextCompat.getColor(this, R.color.red)
            colorCurrent(color)
        }
        binding.colorOrange.setOnClickListener {
            Toast.makeText(this, "Orange", Toast.LENGTH_LONG).show()
            val color = ContextCompat.getColor(this, R.color.orange)
            colorCurrent(color)

        }
        binding.colorYellow.setOnClickListener {
            Toast.makeText(this, "Yellow", Toast.LENGTH_LONG).show()
            val color = ContextCompat.getColor(this, R.color.yellow)
            colorCurrent(color)
        }
        binding.colorGreen.setOnClickListener {
            Toast.makeText(this, "Green", Toast.LENGTH_LONG).show()
            val color = ContextCompat.getColor(this, R.color.green)
            colorCurrent(color)
        }
        binding.colorBlue.setOnClickListener {
            Toast.makeText(this, "Blue", Toast.LENGTH_LONG).show()
            val color = ContextCompat.getColor(this, R.color.blue)
            colorCurrent(color)
        }
        binding.colorPurple.setOnClickListener {
            Toast.makeText(this, "Purple", Toast.LENGTH_LONG).show()
            val color = ContextCompat.getColor(this, R.color.purple)
            colorCurrent(color)
        }
        binding.colorPurple2.setOnClickListener {
            Toast.makeText(this, "Blue Purple", Toast.LENGTH_LONG).show()
            val color = ContextCompat.getColor(this, R.color.purple2)
            colorCurrent(color)
            decreaseBrushSize()
        }
        binding.colorBlack.setOnClickListener {
            Toast.makeText(this, "Black", Toast.LENGTH_LONG).show()
            val color = ContextCompat.getColor(this, R.color.black)
            colorCurrent(color)
            increaseBrushSize()
        }

        binding.colorWhite.setOnClickListener {
            paths.clear()
            colors.clear()
            path.reset()
            binding.rainbowView.invalidate()
        }
    }



    // 2 hàm để tăng giảm độ đậm nhạt của đường vẽ
    private fun increaseBrushSize() {
        paint.strokeWidth += 5f
        Toast.makeText(this, "Increased brush size to ${paint.strokeWidth}", Toast.LENGTH_SHORT).show()
    }

    // 2 hàm để tăng giảm độ đậm nhạt của đường vẽ
    private fun decreaseBrushSize() {
        if (paint.strokeWidth > 5f) {
            paint.strokeWidth -= 5f
            Toast.makeText(this, "Decreased brush size to ${paint.strokeWidth}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Minimum brush size reached", Toast.LENGTH_SHORT).show()
        }
    }
}
