package com.shopika.app.data.repository

import com.shopika.app.data.local.dao.ProductDao
import com.shopika.app.data.local.entity.ProductEntity
import com.shopika.app.data.model.ProductDto
import com.shopika.app.data.remote.ApiService
import com.shopika.app.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(
    private val apiService: ApiService,
    private val productDao: ProductDao
) {
    suspend fun getProducts(search: String? = null, category: String? = null): Resource<List<ProductDto>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProducts(search, category)
                if (response.isSuccessful && response.body() != null) {
                    val products = response.body()!!
                    // Simpan ke Room Cache
                    productDao.insertAll(products.map { ProductEntity.fromDto(it) })
                    Resource.Success(products)
                } else {
                    // Fallback ke Room jika API gagal
                    val localProducts = when {
                        !search.isNullOrBlank() -> productDao.search(search).map { it.toDto() }
                        !category.isNullOrBlank() && category != "Semua" -> productDao.getByCategory(category).map { it.toDto() }
                        else -> productDao.getAll().map { it.toDto() }
                    }
                    if (localProducts.isNotEmpty()) {
                        Resource.Success(localProducts)
                    } else {
                        Resource.Error("Gagal memuat produk (${response.code()}): ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                // Fallback ke Room jika offline / ada gangguan jaringan
                val localProducts = when {
                    !search.isNullOrBlank() -> productDao.search(search).map { it.toDto() }
                    !category.isNullOrBlank() && category != "Semua" -> productDao.getByCategory(category).map { it.toDto() }
                    else -> productDao.getAll().map { it.toDto() }
                }
                if (localProducts.isNotEmpty()) {
                    Resource.Success(localProducts)
                } else {
                    Resource.Error("Koneksi gagal: ${e.localizedMessage ?: "Tidak dapat terhubung ke server"}", e)
                }
            }
        }
    }

    suspend fun getProductById(id: String): Resource<ProductDto> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProductById(id)
                if (response.isSuccessful && response.body() != null) {
                    val product = response.body()!!
                    productDao.insertAll(listOf(ProductEntity.fromDto(product)))
                    Resource.Success(product)
                } else {
                    val local = productDao.getById(id)
                    if (local != null) {
                        Resource.Success(local.toDto())
                    } else {
                        Resource.Error("Produk tidak ditemukan")
                    }
                }
            } catch (e: Exception) {
                val local = productDao.getById(id)
                if (local != null) {
                    Resource.Success(local.toDto())
                } else {
                    Resource.Error("Gagal memuat detail produk: ${e.localizedMessage}", e)
                }
            }
        }
    }
}
