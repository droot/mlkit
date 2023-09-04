package com.google.mlkit.vision.demo.data

import androidx.compose.ui.text.capitalize
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "reps", primaryKeys = ["id", "name"])
data class RepItem(
    val id: Int = 0,
    val name: String,
    val quantity: Int,
    val lastModifiedTime: Long
) {
    fun formatLastModifiedAt(): String {
        val simpleDateFormat = SimpleDateFormat("MMMM dd, yyyy HH:mm a")
        return simpleDateFormat.format(lastModifiedTime)
    }

    fun formatActivityName(): String {
        val index = name.indexOf("_")
        val formattedName = if (index == -1) name else name.substring(0, index)
        return formattedName.capitalize()
    }

}

