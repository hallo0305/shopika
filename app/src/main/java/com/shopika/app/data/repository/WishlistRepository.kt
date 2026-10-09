package com.shopika.app.data.repository

import com.shopika.app.data.local.dao.FavoriteDao
import com.shopika.app.data.local.dao.ProductDao
import com.shopika.app.data.local.entity.FavoriteEntity
import com.shopika.app.data.model.ProductDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class WishlistRepository(
    private val favoriteDao: FavoriteDao,
    private val productDao: ProductDao
) {
    fun getFavoriteIdsFlow(): Flow<List<String>> {
        return favoriteDao.getAllFavoriteIdsFlow()
    }

    suspend fun getFavoriteProducts(): List<ProductDto> {
        return withContext(Dispatchers.IO) {
            val ids = favoriteDao.getAllFavoriteIds()
            ids.mapNotNull { id ->
                productDao.getById(id)?.toDto()
            }
        }
    }

    suspend fun isFavorite(productId: String): Boolean {
        return withContext(Dispatchers.IO) {
            favoriteDao.isFavorite(productId)
        }
    }

    suspend fun toggleFavorite(productId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val current = favoriteDao.isFavorite(productId)
            if (current) {
                favoriteDao.delete(productId)
                false
            } else {
                favoriteDao.insert(FavoriteEntity(productId))
                true
            }
        }
    }
}
