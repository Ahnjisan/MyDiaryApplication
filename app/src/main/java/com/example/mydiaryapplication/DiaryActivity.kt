package com.example.mydiaryapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var diaryDao: DiaryDao
    private lateinit var entries: List<DiaryEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "diary-database"
        ).build()

        diaryDao = db.diaryDao()

        entries = diaryDao.getAll()

        // 리사이클러뷰를 사용하여 entries를 보여주도록 설정합니다.
        // 여기서는 리사이클러뷰 설정 부분이 생략되어 있습니다.
    }
}
