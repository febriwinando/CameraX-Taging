package ac.nan.camerax

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GalleryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnGrid: ImageButton
    private lateinit var btnList: ImageButton

    private var isGrid = true
    private val imageList = mutableListOf<ImageModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        recyclerView = findViewById(R.id.recyclerView)
        btnGrid = findViewById(R.id.btnGrid)
        btnList = findViewById(R.id.btnList)

        loadImages()
        setLayout()

        btnGrid.setOnClickListener {
            isGrid = true
            setLayout()
        }

        btnList.setOnClickListener {
            isGrid = false
            setLayout()
        }
    }

    private fun loadImages() {

        val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        dir?.listFiles()?.forEach {
            if (it.name.startsWith("TAG_")) {
                imageList.add(ImageModel(it.absolutePath))
            }
        }
    }

    private fun setLayout() {

        recyclerView.layoutManager = if (isGrid) {
            GridLayoutManager(this, 2)
        } else {
            LinearLayoutManager(this)
        }

        recyclerView.adapter = ImageAdapter(imageList) { image ->
            val intent = Intent(this, PreviewActivity::class.java)
            intent.putExtra("image_path", image.path)
            startActivity(intent)
        }
    }
}