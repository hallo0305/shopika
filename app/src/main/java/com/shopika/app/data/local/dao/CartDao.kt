package com.shopika.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shopika.app.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getAllFlow(): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart_items")
    suspend fun getAll(): List<CartEntity>

    @Query("SELECT * FROM cart_items WHERE id = :id")
    suspend fun getById(id: String): CartEntity?

    @Query("SELECT * FROM cart_items WHERE productId = :productId AND variant = :variant LIMIT 1")
    suspend fun getByProductAndVariant(productId: String, variant: String): CartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartEntity)

    @Update
    suspend fun update(item: CartEntity)

    @Query("UPDATE cart_items SET quantity = :quantity WHERE id = :id")
    suspend fun updateQuantity(id: String, quantity: Int)

    @Query("UPDATE cart_items SET isSelected = :isSelected WHERE id = :id")
    suspend fun updateSelection(id: String, isSelected: Boolean)

    @Query("UPDATE cart_items SET isSelected = :isSelected")
    suspend fun updateAllSelection(isSelected: Boolean)

    @Query("DELETE FROM cart_items WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM cart_items WHERE isSelected = 1")
    suspend fun deleteSelected()

    @Query("DELETE FROM cart_items")
    suspend fun clearAll()
}
