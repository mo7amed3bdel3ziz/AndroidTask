package com.example.androidtask.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.androidtask.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.flow


fun <Key : Any, Value : Any> PagingSource<Key, Value>.asDataSourceFactory(): Flow<PagingData<Value>> {
    return Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { this }
    ).flow
}

class ItemRepository@Inject constructor(private val itemDao: ItemDao,private val apiService: ApiService) {

    suspend fun insertItem(item: ItemEntity): Boolean {
        val insertedItemId = itemDao.insertItem(item)
        return insertedItemId != -1L // If the ID is -1, insertion failed
    }

//    suspend fun insertAllItems(items: List<ItemEntity>) {
//        itemDao.insertAll(items)
//    }

    suspend fun insertAllItems(items: List<ItemEntity>) {
        val existingIds = itemDao.getAllItemIds()
        val itemsToInsert = items.filter { it.id !in existingIds }
        if (itemsToInsert.isNotEmpty()) {
            itemDao.insertAll(itemsToInsert)
        } else {
            Log.d("DatabaseCheck", "All items already exist in the database")
        }
    }


//    suspend fun getAllItems(): List<ItemEntity> {
//        return withContext(Dispatchers.IO) {
//            itemDao.getAllItems()
//        }
//    }


    fun getAllItems(): LiveData<List<ItemEntity>> {
        return itemDao.getAllItems()
    }

    suspend fun deleteAllItems() {
        itemDao.deleteAllItems()
    }

    fun getAllItemsPaged(): Flow<PagingData<ItemEntity>> {
        return itemDao.getPageAllItems().asDataSourceFactory()
    }


    // Function to get total count of items from the database
    suspend fun getTotalItemsCount(): Int {
        return itemDao.getTotalItemsCount()
    }
    // Add other repository methods as needed




}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}
