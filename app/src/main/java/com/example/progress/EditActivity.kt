package com.example.progress

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.progress.databinding.ActivityEditBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    private lateinit var calendar: Calendar
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    // to save
    private var finalDate: String = ""
    private lateinit var image: Uri
    private val db by lazy { DataDatabase.getDatabase(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imagePickerInitialize()
    }

    // стоит изучить лучше
    fun imagePickerInitialize() {
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val imageUri: Uri? = data?.data
                if (imageUri != null) {
                    binding.plus.visibility = View.GONE
                    image = imageUri
                    binding.imageView2.setImageURI(imageUri)
                }
            }
        }
    }


    fun onImageClick(view: View) = with(binding) {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        imagePickerLauncher.launch(intent)
    }

    fun onDateClick(view: View) {
        calendar = Calendar.getInstance()

        dateSetListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_YEAR, dayOfMonth)

                updateDate()
            }

        val datePickerDialog = DatePickerDialog(
            this, dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_YEAR)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    fun updateDate() {
        val simpleDateFormat = SimpleDateFormat("EEE, d MMM yyyy")
        finalDate = simpleDateFormat.format(calendar.time)
        binding.dateEdt.setText(simpleDateFormat.format(calendar.time))
    }

    fun onSaveClick(view: View) {


        val imageUri = copyFileToAppFolder(image)
        Log.e("tesst", imageUri)

        CoroutineScope(Dispatchers.IO).launch {
            db.dataDao().addData(
                DataModel(
                    finalDate,
                    binding.textInputEditText.text.toString(),
                    imageUri
                )
            )
            this@EditActivity.finish()
        }


    }

    private fun copyFileToAppFolder(uri: Uri): String {
        val inputStream = contentResolver.openInputStream(uri)
        val fileName = getFileName(uri)
        val appFolder = File(filesDir, "images")
        if (!appFolder.exists()) {
            appFolder.mkdir()
        }
        val newFile = File(appFolder, fileName)
        val outputStream = FileOutputStream(newFile)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()

        return newFile.absolutePath
    }

    // learn about
    @SuppressLint("Range")
    private fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            cursor.use { c ->
                if (c != null && c.moveToFirst()) {
                    result = c.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                result = result?.substring(cut!! + 1)
            }
        }
        return result ?: "unknown"
    }


}
