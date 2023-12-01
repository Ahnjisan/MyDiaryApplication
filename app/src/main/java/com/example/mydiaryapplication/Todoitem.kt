package com.example.mydiaryapplication

import android.content.Context
import androidx.core.content.ContextCompat
import java.util.UUID

class Todoitem (
    var name: String = "",
    var selectedDate: String? = null,
    var isCompleted: Boolean = false,
    var id: String = ""

)
{
    fun imageResource(): Int = if(isCompleted) R.drawable.checked_24 else R.drawable.unchecked_24
    fun imageColor(context: Context): Int = if(isCompleted) purple(context) else black(context)

    private fun purple(context: Context) = ContextCompat.getColor(context, R.color.purple_500)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)
}