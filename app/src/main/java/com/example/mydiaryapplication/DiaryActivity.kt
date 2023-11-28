package com.example.mydiaryapplication

import android.content.Intent
import com.bumptech.glide.Glide
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class DiaryActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var saveButton: Button
    private var selectedDate: String? = null
    private var imageUrl: String? = null
    private lateinit var selectImageButton: Button


    companion object {
        private const val IMAGE_PICK_CODE = 1001 // 이미지 선택을 위한 요청 코드
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        selectImageButton = findViewById(R.id.selectImageButton)
        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }

        database = FirebaseDatabase.getInstance().getReference("diaries")
        storageReference = FirebaseStorage.getInstance().getReference("diary_images")

        titleEditText = findViewById(R.id.titleEditText)
        contentEditText = findViewById(R.id.contentEditText)
        imageView = findViewById(R.id.imageView)
        saveButton = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            val diary = Diary(
                date = selectedDate ?: "",
                title = titleEditText.text.toString(),
                content = contentEditText.text.toString(),
                imageUrl = imageUrl
            )
            selectedDate?.let {
                database.child(it).setValue(diary)
            }
            // Optionally finish the activity
        }

        selectedDate = intent.getStringExtra("SELECTED_DATE")

        loadDiaryData(selectedDate)
    }

    private fun loadDiaryData(date: String?) {
        date?.let {
            database.child(date).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val diary = snapshot.getValue(Diary::class.java)
                    diary?.let {
                        titleEditText.setText(diary.title)
                        contentEditText.setText(diary.content)
                        if (diary.imageUrl != null) {
                            loadDiaryImage(diary.imageUrl)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors
                }
            })
        }
    }
    private fun loadDiaryImage(imageUrl: String?) {
        imageUrl?.let { url ->
            Glide.with(this)
                .load(url)
                .into(imageView)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                uploadImageToFirebase(uri)
            }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val imageRef = storageReference.child(selectedDate + "/" + UUID.randomUUID().toString())
        imageRef.putFile(imageUri).addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                imageUrl = uri.toString()
                imageView.setImageURI(imageUri)
            }
        }.addOnFailureListener {
            // Handle failures
        }
    }

}
