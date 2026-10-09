package com.shopika.app.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.shopika.app.`data`.local.entity.CartEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
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
public class CartDao_Impl(
  __db: RoomDatabase,
) : CartDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfCartEntity: EntityInsertAdapter<CartEntity>

  private val __updateAdapterOfCartEntity: EntityDeleteOrUpdateAdapter<CartEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfCartEntity = object : EntityInsertAdapter<CartEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `cart_items` (`id`,`productId`,`productName`,`productPrice`,`productImage`,`variant`,`quantity`,`stock`,`isSelected`) VALUES (?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: CartEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.productId)
        statement.bindText(3, entity.productName)
        statement.bindLong(4, entity.productPrice)
        statement.bindText(5, entity.productImage)
        statement.bindText(6, entity.variant)
        statement.bindLong(7, entity.quantity.toLong())
        statement.bindLong(8, entity.stock.toLong())
        val _tmp: Int = if (entity.isSelected) 1 else 0
        statement.bindLong(9, _tmp.toLong())
      }
    }
    this.__updateAdapterOfCartEntity = object : EntityDeleteOrUpdateAdapter<CartEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `cart_items` SET `id` = ?,`productId` = ?,`productName` = ?,`productPrice` = ?,`productImage` = ?,`variant` = ?,`quantity` = ?,`stock` = ?,`isSelected` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: CartEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.productId)
        statement.bindText(3, entity.productName)
        statement.bindLong(4, entity.productPrice)
        statement.bindText(5, entity.productImage)
        statement.bindText(6, entity.variant)
        statement.bindLong(7, entity.quantity.toLong())
        statement.bindLong(8, entity.stock.toLong())
        val _tmp: Int = if (entity.isSelected) 1 else 0
        statement.bindLong(9, _tmp.toLong())
        statement.bindText(10, entity.id)
      }
    }
  }

  public override suspend fun insert(item: CartEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfCartEntity.insert(_connection, item)
  }

  public override suspend fun update(item: CartEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfCartEntity.handle(_connection, item)
  }

  public override fun getAllFlow(): Flow<List<CartEntity>> {
    val _sql: String = "SELECT * FROM cart_items"
    return createFlow(__db, false, arrayOf("cart_items")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfProductName: Int = getColumnIndexOrThrow(_stmt, "productName")
        val _columnIndexOfProductPrice: Int = getColumnIndexOrThrow(_stmt, "productPrice")
        val _columnIndexOfProductImage: Int = getColumnIndexOrThrow(_stmt, "productImage")
        val _columnIndexOfVariant: Int = getColumnIndexOrThrow(_stmt, "variant")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfStock: Int = getColumnIndexOrThrow(_stmt, "stock")
        val _columnIndexOfIsSelected: Int = getColumnIndexOrThrow(_stmt, "isSelected")
        val _result: MutableList<CartEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: CartEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpProductId: String
          _tmpProductId = _stmt.getText(_columnIndexOfProductId)
          val _tmpProductName: String
          _tmpProductName = _stmt.getText(_columnIndexOfProductName)
          val _tmpProductPrice: Long
          _tmpProductPrice = _stmt.getLong(_columnIndexOfProductPrice)
          val _tmpProductImage: String
          _tmpProductImage = _stmt.getText(_columnIndexOfProductImage)
          val _tmpVariant: String
          _tmpVariant = _stmt.getText(_columnIndexOfVariant)
          val _tmpQuantity: Int
          _tmpQuantity = _stmt.getLong(_columnIndexOfQuantity).toInt()
          val _tmpStock: Int
          _tmpStock = _stmt.getLong(_columnIndexOfStock).toInt()
          val _tmpIsSelected: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsSelected).toInt()
          _tmpIsSelected = _tmp != 0
          _item = CartEntity(_tmpId,_tmpProductId,_tmpProductName,_tmpProductPrice,_tmpProductImage,_tmpVariant,_tmpQuantity,_tmpStock,_tmpIsSelected)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<CartEntity> {
    val _sql: String = "SELECT * FROM cart_items"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfProductName: Int = getColumnIndexOrThrow(_stmt, "productName")
        val _columnIndexOfProductPrice: Int = getColumnIndexOrThrow(_stmt, "productPrice")
        val _columnIndexOfProductImage: Int = getColumnIndexOrThrow(_stmt, "productImage")
        val _columnIndexOfVariant: Int = getColumnIndexOrThrow(_stmt, "variant")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfStock: Int = getColumnIndexOrThrow(_stmt, "stock")
        val _columnIndexOfIsSelected: Int = getColumnIndexOrThrow(_stmt, "isSelected")
        val _result: MutableList<CartEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: CartEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpProductId: String
          _tmpProductId = _stmt.getText(_columnIndexOfProductId)
          val _tmpProductName: String
          _tmpProductName = _stmt.getText(_columnIndexOfProductName)
          val _tmpProductPrice: Long
          _tmpProductPrice = _stmt.getLong(_columnIndexOfProductPrice)
          val _tmpProductImage: String
          _tmpProductImage = _stmt.getText(_columnIndexOfProductImage)
          val _tmpVariant: String
          _tmpVariant = _stmt.getText(_columnIndexOfVariant)
          val _tmpQuantity: Int
          _tmpQuantity = _stmt.getLong(_columnIndexOfQuantity).toInt()
          val _tmpStock: Int
          _tmpStock = _stmt.getLong(_columnIndexOfStock).toInt()
          val _tmpIsSelected: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsSelected).toInt()
          _tmpIsSelected = _tmp != 0
          _item = CartEntity(_tmpId,_tmpProductId,_tmpProductName,_tmpProductPrice,_tmpProductImage,_tmpVariant,_tmpQuantity,_tmpStock,_tmpIsSelected)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: String): CartEntity? {
    val _sql: String = "SELECT * FROM cart_items WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfProductName: Int = getColumnIndexOrThrow(_stmt, "productName")
        val _columnIndexOfProductPrice: Int = getColumnIndexOrThrow(_stmt, "productPrice")
        val _columnIndexOfProductImage: Int = getColumnIndexOrThrow(_stmt, "productImage")
        val _columnIndexOfVariant: Int = getColumnIndexOrThrow(_stmt, "variant")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfStock: Int = getColumnIndexOrThrow(_stmt, "stock")
        val _columnIndexOfIsSelected: Int = getColumnIndexOrThrow(_stmt, "isSelected")
        val _result: CartEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpProductId: String
          _tmpProductId = _stmt.getText(_columnIndexOfProductId)
          val _tmpProductName: String
          _tmpProductName = _stmt.getText(_columnIndexOfProductName)
          val _tmpProductPrice: Long
          _tmpProductPrice = _stmt.getLong(_columnIndexOfProductPrice)
          val _tmpProductImage: String
          _tmpProductImage = _stmt.getText(_columnIndexOfProductImage)
          val _tmpVariant: String
          _tmpVariant = _stmt.getText(_columnIndexOfVariant)
          val _tmpQuantity: Int
          _tmpQuantity = _stmt.getLong(_columnIndexOfQuantity).toInt()
          val _tmpStock: Int
          _tmpStock = _stmt.getLong(_columnIndexOfStock).toInt()
          val _tmpIsSelected: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsSelected).toInt()
          _tmpIsSelected = _tmp != 0
          _result = CartEntity(_tmpId,_tmpProductId,_tmpProductName,_tmpProductPrice,_tmpProductImage,_tmpVariant,_tmpQuantity,_tmpStock,_tmpIsSelected)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getByProductAndVariant(productId: String, variant: String): CartEntity? {
    val _sql: String = "SELECT * FROM cart_items WHERE productId = ? AND variant = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, productId)
        _argIndex = 2
        _stmt.bindText(_argIndex, variant)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfProductName: Int = getColumnIndexOrThrow(_stmt, "productName")
        val _columnIndexOfProductPrice: Int = getColumnIndexOrThrow(_stmt, "productPrice")
        val _columnIndexOfProductImage: Int = getColumnIndexOrThrow(_stmt, "productImage")
        val _columnIndexOfVariant: Int = getColumnIndexOrThrow(_stmt, "variant")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfStock: Int = getColumnIndexOrThrow(_stmt, "stock")
        val _columnIndexOfIsSelected: Int = getColumnIndexOrThrow(_stmt, "isSelected")
        val _result: CartEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpProductId: String
          _tmpProductId = _stmt.getText(_columnIndexOfProductId)
          val _tmpProductName: String
          _tmpProductName = _stmt.getText(_columnIndexOfProductName)
          val _tmpProductPrice: Long
          _tmpProductPrice = _stmt.getLong(_columnIndexOfProductPrice)
          val _tmpProductImage: String
          _tmpProductImage = _stmt.getText(_columnIndexOfProductImage)
          val _tmpVariant: String
          _tmpVariant = _stmt.getText(_columnIndexOfVariant)
          val _tmpQuantity: Int
          _tmpQuantity = _stmt.getLong(_columnIndexOfQuantity).toInt()
          val _tmpStock: Int
          _tmpStock = _stmt.getLong(_columnIndexOfStock).toInt()
          val _tmpIsSelected: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsSelected).toInt()
          _tmpIsSelected = _tmp != 0
          _result = CartEntity(_tmpId,_tmpProductId,_tmpProductName,_tmpProductPrice,_tmpProductImage,_tmpVariant,_tmpQuantity,_tmpStock,_tmpIsSelected)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateQuantity(id: String, quantity: Int) {
    val _sql: String = "UPDATE cart_items SET quantity = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, quantity.toLong())
        _argIndex = 2
        _stmt.bindText(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateSelection(id: String, isSelected: Boolean) {
    val _sql: String = "UPDATE cart_items SET isSelected = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Int = if (isSelected) 1 else 0
        _stmt.bindLong(_argIndex, _tmp.toLong())
        _argIndex = 2
        _stmt.bindText(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateAllSelection(isSelected: Boolean) {
    val _sql: String = "UPDATE cart_items SET isSelected = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Int = if (isSelected) 1 else 0
        _stmt.bindLong(_argIndex, _tmp.toLong())
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteById(id: String) {
    val _sql: String = "DELETE FROM cart_items WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteSelected() {
    val _sql: String = "DELETE FROM cart_items WHERE isSelected = 1"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun clearAll() {
    val _sql: String = "DELETE FROM cart_items"
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
