package ac.nan.camerax

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class PreviewActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnShare: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        imageView = findViewById(R.id.imageView)
        btnShare = findViewById(R.id.btnShare)

        val path = intent.getStringExtra("image_path")

        val bitmap = BitmapFactory.decodeFile(path)
        imageView.setImageBitmap(bitmap)

        btnShare.setOnClickListener {
            shareImage(path!!)
        }
    }

    private fun shareImage(path: String) {

        val file = File(path)
        val uri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(Intent.createChooser(intent, "Share Image"))
    }
}