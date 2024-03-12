package com.example.androidtask.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ItemEntity>)

    @Query("SELECT id FROM items")
    suspend fun getAllItemIds(): List<Long>

//    @Query("SELECT * FROM items")
//    suspend fun getAllItems(): List<ItemEntity>

    @Query("SELECT * FROM items")
    fun getAllItems(): LiveData<List<ItemEntity>>

    @Query("DELETE FROM items")
    suspend fun deleteAllItems()


    @Query("SELECT * FROM items")
    fun getPageAllItems(): PagingSource<Int, ItemEntity>

    @Query("SELECT COUNT(*) FROM items")
    suspend fun getTotalItemsCount(): Int

}
