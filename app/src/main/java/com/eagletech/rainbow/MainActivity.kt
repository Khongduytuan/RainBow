package com.eagletech.rainbow

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.eagletech.rainbow.customview.RainBowView.Companion.brush
import com.eagletech.rainbow.customview.RainBowView.Companion.drawDataList
import com.eagletech.rainbow.databinding.ActivityMainBinding
import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.eagletech.rainbow.customview.RainBowView
import com.eagletech.rainbow.dataApp.MyData
import java.io.OutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myData: MyData

    companion object {
        var path = Path()
        var paint = Paint()
    }

    private val REQUEST_CODE_PERMISSIONS = 101
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myData = MyData.getInstance(this)
        setClick()
        funUI()
    }

    private fun colorCurrent(color: Int) {
        brush = color
        path = Path()
        paint = Paint().apply {
            isAntiAlias = true
            this.color = brush
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeWidth = paint.strokeWidth
        }
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
        }
        binding.colorBlack.setOnClickListener {
            Toast.makeText(this, "Black", Toast.LENGTH_LONG).show()
            val color = ContextCompat.getColor(this, R.color.black)
            colorCurrent(color)

        }

        binding.plus.setOnClickListener { increaseBrushSize() }
        binding.notplus.setOnClickListener { decreaseBrushSize() }
        binding.colorWhite.setOnClickListener {
            drawDataList.clear()
            path.reset()
            binding.rainbowView.invalidate()
        }
        binding.topBarApp.dowloadIcon.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveDrawingToGallery()
            } else {
                if (checkPermissions()) {
                    saveDrawingToGallery()
                } else {
                    requestPermissions()
                }
            }
        }
        binding.topBarApp.menuIcon.setOnClickListener {
            val intent = Intent(this, MoneyActivity::class.java)
            startActivity(intent)
        }
        binding.topBarApp.infoIcon.setOnClickListener {
            showInfoDialog()
        }
    }


    private fun funUI() {
        if (myData.isPremiumSaves == true){
            binding.topBarApp.dowloadIcon.visibility = View.VISIBLE

        }else{
            if ((myData.getSaves() > 0)) {
                binding.topBarApp.dowloadIcon.visibility = View.VISIBLE

            } else {
                binding.topBarApp.dowloadIcon.visibility = View.GONE
            }
        }
    }

    // Show dialog cho dữ liệu SharePreferences
    private fun showInfoDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Information")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
        if (myData.isPremiumSaves == true){
            dialog.setMessage("You have successfully registered")
        }else{
            dialog.setMessage("You have ${myData.getSaves()} turns")
        }
        dialog.show()
    }

    private fun checkPermissions(): Boolean {
        return REQUIRED_PERMISSIONS.all { permission: String ->
            ContextCompat.checkSelfPermission(this, permission ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                saveDrawingToGallery()
            } else {
                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Lưu ảnh từ DrawView vào thư viện
    private fun saveDrawingToGallery() {
        if (!checkPermissions()) {
            requestPermissions()
            return
        }
        val drawView = findViewById<RainBowView>(R.id.rainbowView)
        val bitmap = Bitmap.createBitmap(drawView.width, drawView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawView.draw(canvas)

        val filename = "drawing_${System.currentTimeMillis()}.png"
        val fos: OutputStream?
        val resolver = contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MyDrawings") // Tùy chỉnh đường dẫn theo nhu cầu của bạn
        }
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        fos = imageUri?.let { resolver.openOutputStream(it) }

        if (myData.getSaves() > 0 || myData.isPremiumSaves == true){
            fos?.use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_SHORT).show()
                myData.removeSaves()
            } ?: run {
                Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "You have run out of saves", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        funUI()
    }

    private fun increaseBrushSize() {
        paint.strokeWidth += 5f
        Toast.makeText(this, "Increased brush size to ${paint.strokeWidth}", Toast.LENGTH_SHORT).show()
    }
    private fun decreaseBrushSize() {
        if (paint.strokeWidth > 5f) {
            paint.strokeWidth -= 5f
            Toast.makeText(this, "Decreased brush size to ${paint.strokeWidth}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Minimum brush size reached", Toast.LENGTH_SHORT).show()
        }
    }
}
