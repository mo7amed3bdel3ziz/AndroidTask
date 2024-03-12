package com.example.androidtask.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "itemName")
    var itemName        : String,
    @ColumnInfo(name = "barcode")
    var barCode        : String
)
