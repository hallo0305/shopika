package com.shopika.app.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.shopika.app.`data`.local.entity.ProductEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ProductDao_Impl(
  __db: RoomDatabase,
) : ProductDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfProductEntity: EntityInsertAdapter<ProductEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfProductEntity = object : EntityInsertAdapter<ProductEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `products` (`id`,`name`,`description`,`price`,`originalPrice`,`discountPercent`,`category`,`imageUrl`,`rating`,`soldCount`,`stock`,`variantsCsv`,`isPopular`,`isRecommended`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ProductEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.description)
        statement.bindLong(4, entity.price)
        val _tmpOriginalPrice: Long? = entity.originalPrice
        if (_tmpOriginalPrice == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpOriginalPrice)
        }
        statement.bindLong(6, entity.discountPercent.toLong())
        statement.bindText(7, entity.category)
        statement.bindText(8, entity.imageUrl)
        statement.bindDouble(9, entity.rating)
        statement.bindLong(10, entity.soldCount.toLong())
        statement.bindLong(11, entity.stock.toLong())
        statement.bindText(12, entity.variantsCsv)
        val _tmp: Int = if (entity.isPopular) 1 else 0
        statement.bindLong(13, _tmp.toLong())
        val _tmp_1: Int = if (entity.isRecommended) 1 else 0
        statement.bindLong(14, _tmp_1.toLong())
      }
    }
  }

  public override suspend fun insertAll(products: List<ProductEntity>): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfProductEntity.insert(_connection, products)
  }

  public override fun getAllFlow(): Flow<List<ProductEntity>> {
    val _sql: String = "SELECT * FROM products"
    return createFlow(__db, false, arrayOf("products")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfPrice: Int = getColumnIndexOrThrow(_stmt, "price")
        val _columnIndexOfOriginalPrice: Int = getColumnIndexOrThrow(_stmt, "originalPrice")
        val _columnIndexOfDiscountPercent: Int = getColumnIndexOrThrow(_stmt, "discountPercent")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfRating: Int = getColumnIndexOrThrow(_stmt, "rating")
        val _columnIndexOfSoldCount: Int = getColumnIndexOrThrow(_stmt, "soldCount")
        val _columnIndexOfStock: Int = getColumnIndexOrThrow(_stmt, "stock")
        val _columnIndexOfVariantsCsv: Int = getColumnIndexOrThrow(_stmt, "variantsCsv")
        val _columnIndexOfIsPopular: Int = getColumnIndexOrThrow(_stmt, "isPopular")
        val _columnIndexOfIsRecommended: Int = getColumnIndexOrThrow(_stmt, "isRecommended")
        val _result: MutableList<ProductEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ProductEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpPrice: Long
          _tmpPrice = _stmt.getLong(_columnIndexOfPrice)
          val _tmpOriginalPrice: Long?
          if (_stmt.isNull(_columnIndexOfOriginalPrice)) {
            _tmpOriginalPrice = null
          } else {
            _tmpOriginalPrice = _stmt.getLong(_columnIndexOfOriginalPrice)
          }
          val _tmpDiscountPercent: Int
          _tmpDiscountPercent = _stmt.getLong(_columnIndexOfDiscountPercent).toInt()
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpImageUrl: String
          _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          val _tmpRating: Double
          _tmpRating = _stmt.getDouble(_columnIndexOfRating)
          val _tmpSoldCount: Int
          _tmpSoldCount = _stmt.getLong(_columnIndexOfSoldCount).toInt()
          val _tmpStock: Int
          _tmpStock = _stmt.getLong(_columnIndexOfStock).toInt()
          val _tmpVariantsCsv: String
          _tmpVariantsCsv = _stmt.getText(_columnIndexOfVariantsCsv)
          val _tmpIsPopular: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsPopular).toInt()
          _tmpIsPopular = _tmp != 0
          val _tmpIsRecommended: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsRecommended).toInt()
          _tmpIsRecommended = _tmp_1 != 0
          _item = ProductEntity(_tmpId,_tmpName,_tmpDescription,_tmpPrice,_tmpOriginalPrice,_tmpDiscountPercent,_tmpCategory,_tmpImageUrl,_tmpRating,_tmpSoldCount,_tmpStock,_tmpVariantsCsv,_tmpIsPopular,_tmpIsRecommended)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<ProductEntity> {
    val _sql: String = "SELECT * FROM products"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfPrice: Int = getColumnIndexOrThrow(_stmt, "price")
        val _columnIndexOfOriginalPrice: Int = getColumnIndexOrThrow(_stmt, "originalPrice")
        val _columnIndexOfDiscountPercent: Int = getColumnIndexOrThrow(_stmt, "discountPercent")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfRating: Int = getColumnIndexOrThrow(_stmt, "rating")
        val _columnIndexOfSoldCount: Int = getColumnIndexOrThrow(_stmt, "soldCount")
        val _columnIndexOfStock: Int = getColumnIndexOrThrow(_stmt, "stock")
        val _columnIndexOfVariantsCsv: Int = getColumnIndexOrThrow(_stmt, "variantsCsv")
        val _columnIndexOfIsPopular: Int = getColumnIndexOrThrow(_stmt, "isPopular")
        val _columnIndexOfIsRecommended: Int = getColumnIndexOrThrow(_stmt, "isRecommended")
        val _result: MutableList<ProductEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ProductEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpPrice: Long
          _tmpPrice = _stmt.getLong(_columnIndexOfPrice)
          val _tmpOriginalPrice: Long?
          if (_stmt.isNull(_columnIndexOfOriginalPrice)) {
            _tmpOriginalPrice = null
          } else {
            _tmpOriginalPrice = _stmt.getLong(_columnIndexOfOriginalPrice)
          }
          val _tmpDiscountPercent: Int
          _tmpDiscountPercent = _stmt.getLong(_columnIndexOfDiscountPercent).toInt()
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpImageUrl: String
          _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          val _tmpRating: Double
          _tmpRating = _stmt.getDouble(_columnIndexOfRating)
          val _tmpSoldCount: Int
          _tmpSoldCount = _stmt.getLong(_columnIndexOfSoldCount).toInt()
          val _tmpStock: Int
          _tmpStock = _stmt.getLong(_columnIndexOfStock).toInt()
          val _tmpVariantsCsv: String
          _tmpVariantsCsv = _stmt.getText(_columnIndexOfVariantsCsv)
          val _tmpIsPopular: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsPopular).toInt()
          _tmpIsPopular = _tmp != 0
          val _tmpIsRecommended: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsRecommended).toInt()
          _tmpIsRecommended = _tmp_1 != 0
          _item = ProductEntity(_tmpId,_tmpName,_tmpDescription,_tmpPrice,_tmpOriginalPrice,_tmpDiscountPercent,_tmpCategory,_tmpImageUrl,_tmpRating,_tmpSoldCount,_tmpStock,_tmpVariantsCsv,_tmpIsPopular,_tmpIsRecommended)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: String): ProductEntity? {
    val _sql: String = "SELECT * FROM products WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfPrice: Int = getColumnIndexOrThrow(_stmt, "price")
        val _columnIndexOfOriginalPrice: Int = getColumnIndexOrThrow(_stmt, "originalPrice")
        val _columnIndexOfDiscountPercent: Int = getColumnIndexOrThrow(_stmt, "discountPercent")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfRating: Int = getColumnIndexOrThrow(_stmt, "rating")
        val _columnIndexOfSoldCount: Int = getColumnIndexOrThrow(_stmt, "soldCount")
        val _columnIndexOfStock: Int = getColumnIndexOrThrow(_stmt, "stock")
        val _columnIndexOfVariantsCsv: Int = getColumnIndexOrThrow(_stmt, "variantsCsv")
        val _columnIndexOfIsPopular: Int = getColumnIndexOrThrow(_stmt, "isPopular")
        val _columnIndexOfIsRecommended: Int = getColumnIndexOrThrow(_stmt, "isRecommended")
        val _result: ProductEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpPrice: Long
          _tmpPrice = _stmt.getLong(_columnIndexOfPrice)
          val _tmpOriginalPrice: Long?
          if (_stmt.isNull(_columnIndexOfOriginalPrice)) {
            _tmpOriginalPrice = null
          } else {
            _tmpOriginalPrice = _stmt.getLong(_columnIndexOfOriginalPrice)
          }
          val _tmpDiscountPercent: Int
          _tmpDiscountPercent = _stmt.getLong(_columnIndexOfDiscountPercent).toInt()
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpImageUrl: String
          _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          val _tmpRating: Double
          _tmpRating = _stmt.getDouble(_columnIndexOfRating)
          val _tmpSoldCount: Int
          _tmpSoldCount = _stmt.getLong(_columnIndexOfSoldCount).toInt()
          val _tmpStock: Int
          _tmpStock = _stmt.getLong(_columnIndexOfStock).toInt()
          val _tmpVariantsCsv: String
          _tmpVariantsCsv = _stmt.getText(_columnIndexOfVariantsCsv)
          val _tmpIsPopular: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsPopular).toInt()
          _tmpIsPopular = _tmp != 0
          val _tmpIsRecommended: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsRecommended).toInt()
          _tmpIsRecommended = _tmp_1 != 0
          _result = ProductEntity(_tmpId,_tmpName,_tmpDescription,_tmpPrice,_tmpOriginalPrice,_tmpDiscountPercent,_tmpCategory,_tmpImageUrl,_tmpRating,_tmpSoldCount,_tmpStock,_tmpVariantsCsv,_tmpIsPopular,_tmpIsRecommended)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getByCategory(category: String): List<ProductEntity> {
    val _sql: String = "SELECT * FROM products WHERE category = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, category)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfPrice: Int = getColumnIndexOrThrow(_stmt, "price")
        val _columnIndexOfOriginalPrice: Int = getColumnIndexOrThrow(_stmt, "originalPrice")
        val _columnIndexOfDiscountPercent: Int = getColumnIndexOrThrow(_stmt, "discountPercent")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfRating: Int = getColumnIndexOrThrow(_stmt, "rating")
        val _columnIndexOfSoldCount: Int = getColumnIndexOrThrow(_stmt, "soldCount")
        val _columnIndexOfStock: Int = getColumnIndexOrThrow(_stmt, "stock")
        val _columnIndexOfVariantsCsv: Int = getColumnIndexOrThrow(_stmt, "variantsCsv")
        val _columnIndexOfIsPopular: Int = getColumnIndexOrThrow(_stmt, "isPopular")
        val _columnIndexOfIsRecommended: Int = getColumnIndexOrThrow(_stmt, "isRecommended")
        val _result: MutableList<ProductEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ProductEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpPrice: Long
          _tmpPrice = _stmt.getLong(_columnIndexOfPrice)
          val _tmpOriginalPrice: Long?
          if (_stmt.isNull(_columnIndexOfOriginalPrice)) {
            _tmpOriginalPrice = null
          } else {
            _tmpOriginalPrice = _stmt.getLong(_columnIndexOfOriginalPrice)
          }
          val _tmpDiscountPercent: Int
          _tmpDiscountPercent = _stmt.getLong(_columnIndexOfDiscountPercent).toInt()
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpImageUrl: String
          _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          val _tmpRating: Double
          _tmpRating = _stmt.getDouble(_columnIndexOfRating)
          val _tmpSoldCount: Int
          _tmpSoldCount = _stmt.getLong(_columnIndexOfSoldCount).toInt()
          val _tmpStock: Int
          _tmpStock = _stmt.getLong(_columnIndexOfStock).toInt()
          val _tmpVariantsCsv: String
          _tmpVariantsCsv = _stmt.getText(_columnIndexOfVariantsCsv)
          val _tmpIsPopular: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsPopular).toInt()
          _tmpIsPopular = _tmp != 0
          val _tmpIsRecommended: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsRecommended).toInt()
          _tmpIsRecommended = _tmp_1 != 0
          _item = ProductEntity(_tmpId,_tmpName,_tmpDescription,_tmpPrice,_tmpOriginalPrice,_tmpDiscountPercent,_tmpCategory,_tmpImageUrl,_tmpRating,_tmpSoldCount,_tmpStock,_tmpVariantsCsv,_tmpIsPopular,_tmpIsRecommended)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun search(query: String): List<ProductEntity> {
    val _sql: String = "SELECT * FROM products WHERE name LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%'"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfPrice: Int = getColumnIndexOrThrow(_stmt, "price")
        val _columnIndexOfOriginalPrice: Int = getColumnIndexOrThrow(_stmt, "originalPrice")
        val _columnIndexOfDiscountPercent: Int = getColumnIndexOrThrow(_stmt, "discountPercent")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfRating: Int = getColumnIndexOrThrow(_stmt, "rating")
        val _columnIndexOfSoldCount: Int = getColumnIndexOrThrow(_stmt, "soldCount")
        val _columnIndexOfStock: Int = getColumnIndexOrThrow(_stmt, "stock")
        val _columnIndexOfVariantsCsv: Int = getColumnIndexOrThrow(_stmt, "variantsCsv")
        val _columnIndexOfIsPopular: Int = getColumnIndexOrThrow(_stmt, "isPopular")
        val _columnIndexOfIsRecommended: Int = getColumnIndexOrThrow(_stmt, "isRecommended")
        val _result: MutableList<ProductEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ProductEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpPrice: Long
          _tmpPrice = _stmt.getLong(_columnIndexOfPrice)
          val _tmpOriginalPrice: Long?
          if (_stmt.isNull(_columnIndexOfOriginalPrice)) {
            _tmpOriginalPrice = null
          } else {
            _tmpOriginalPrice = _stmt.getLong(_columnIndexOfOriginalPrice)
          }
          val _tmpDiscountPercent: Int
          _tmpDiscountPercent = _stmt.getLong(_columnIndexOfDiscountPercent).toInt()
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpImageUrl: String
          _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          val _tmpRating: Double
          _tmpRating = _stmt.getDouble(_columnIndexOfRating)
          val _tmpSoldCount: Int
          _tmpSoldCount = _stmt.getLong(_columnIndexOfSoldCount).toInt()
          val _tmpStock: Int
          _tmpStock = _stmt.getLong(_columnIndexOfStock).toInt()
          val _tmpVariantsCsv: String
          _tmpVariantsCsv = _stmt.getText(_columnIndexOfVariantsCsv)
          val _tmpIsPopular: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsPopular).toInt()
          _tmpIsPopular = _tmp != 0
          val _tmpIsRecommended: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsRecommended).toInt()
          _tmpIsRecommended = _tmp_1 != 0
          _item = ProductEntity(_tmpId,_tmpName,_tmpDescription,_tmpPrice,_tmpOriginalPrice,_tmpDiscountPercent,_tmpCategory,_tmpImageUrl,_tmpRating,_tmpSoldCount,_tmpStock,_tmpVariantsCsv,_tmpIsPopular,_tmpIsRecommended)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAll() {
    val _sql: String = "DELETE FROM products"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
