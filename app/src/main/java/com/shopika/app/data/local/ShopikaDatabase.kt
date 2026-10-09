package com.shopika.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shopika.app.data.local.dao.CartDao
import com.shopika.app.data.local.dao.FavoriteDao
import com.shopika.app.data.local.dao.OrderDao
import com.shopika.app.data.local.dao.ProductDao
import com.shopika.app.data.local.entity.CartEntity
import com.shopika.app.data.local.entity.FavoriteEntity
import com.shopika.app.data.local.entity.OrderEntity
import com.shopika.app.data.local.entity.ProductEntity

@Database(
    entities = [
        ProductEntity::class,
        CartEntity::class,
        OrderEntity::class,
        FavoriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ShopikaDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: ShopikaDatabase? = null

        fun getDatabase(context: Context): ShopikaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShopikaDatabase::class.java,
                    "shopika_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
