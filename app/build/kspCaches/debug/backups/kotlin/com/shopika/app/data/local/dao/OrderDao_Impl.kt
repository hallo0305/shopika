package com.shopika.app.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.shopika.app.`data`.local.entity.OrderEntity
import javax.`annotation`.processing.Generated
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
public class OrderDao_Impl(
  __db: RoomDatabase,
) : OrderDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfOrderEntity: EntityInsertAdapter<OrderEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfOrderEntity = object : EntityInsertAdapter<OrderEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `orders` (`id`,`orderNumber`,`itemsJson`,`subtotal`,`shippingFee`,`totalAmount`,`status`,`paymentMethod`,`vaNumber`,`shippingAddress`,`recipientName`,`recipientPhone`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: OrderEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.orderNumber)
        statement.bindText(3, entity.itemsJson)
        statement.bindLong(4, entity.subtotal)
        statement.bindLong(5, entity.shippingFee)
        statement.bindLong(6, entity.totalAmount)
        statement.bindText(7, entity.status)
        statement.bindText(8, entity.paymentMethod)
        statement.bindText(9, entity.vaNumber)
        statement.bindText(10, entity.shippingAddress)
        statement.bindText(11, entity.recipientName)
        statement.bindText(12, entity.recipientPhone)
        statement.bindLong(13, entity.createdAt)
      }
    }
  }

  public override suspend fun insert(order: OrderEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfOrderEntity.insert(_connection, order)
  }

  public override fun getAllFlow(): Flow<List<OrderEntity>> {
    val _sql: String = "SELECT * FROM orders ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("orders")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfOrderNumber: Int = getColumnIndexOrThrow(_stmt, "orderNumber")
        val _columnIndexOfItemsJson: Int = getColumnIndexOrThrow(_stmt, "itemsJson")
        val _columnIndexOfSubtotal: Int = getColumnIndexOrThrow(_stmt, "subtotal")
        val _columnIndexOfShippingFee: Int = getColumnIndexOrThrow(_stmt, "shippingFee")
        val _columnIndexOfTotalAmount: Int = getColumnIndexOrThrow(_stmt, "totalAmount")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfVaNumber: Int = getColumnIndexOrThrow(_stmt, "vaNumber")
        val _columnIndexOfShippingAddress: Int = getColumnIndexOrThrow(_stmt, "shippingAddress")
        val _columnIndexOfRecipientName: Int = getColumnIndexOrThrow(_stmt, "recipientName")
        val _columnIndexOfRecipientPhone: Int = getColumnIndexOrThrow(_stmt, "recipientPhone")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<OrderEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: OrderEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpOrderNumber: String
          _tmpOrderNumber = _stmt.getText(_columnIndexOfOrderNumber)
          val _tmpItemsJson: String
          _tmpItemsJson = _stmt.getText(_columnIndexOfItemsJson)
          val _tmpSubtotal: Long
          _tmpSubtotal = _stmt.getLong(_columnIndexOfSubtotal)
          val _tmpShippingFee: Long
          _tmpShippingFee = _stmt.getLong(_columnIndexOfShippingFee)
          val _tmpTotalAmount: Long
          _tmpTotalAmount = _stmt.getLong(_columnIndexOfTotalAmount)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpPaymentMethod: String
          _tmpPaymentMethod = _stmt.getText(_columnIndexOfPaymentMethod)
          val _tmpVaNumber: String
          _tmpVaNumber = _stmt.getText(_columnIndexOfVaNumber)
          val _tmpShippingAddress: String
          _tmpShippingAddress = _stmt.getText(_columnIndexOfShippingAddress)
          val _tmpRecipientName: String
          _tmpRecipientName = _stmt.getText(_columnIndexOfRecipientName)
          val _tmpRecipientPhone: String
          _tmpRecipientPhone = _stmt.getText(_columnIndexOfRecipientPhone)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = OrderEntity(_tmpId,_tmpOrderNumber,_tmpItemsJson,_tmpSubtotal,_tmpShippingFee,_tmpTotalAmount,_tmpStatus,_tmpPaymentMethod,_tmpVaNumber,_tmpShippingAddress,_tmpRecipientName,_tmpRecipientPhone,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<OrderEntity> {
    val _sql: String = "SELECT * FROM orders ORDER BY createdAt DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfOrderNumber: Int = getColumnIndexOrThrow(_stmt, "orderNumber")
        val _columnIndexOfItemsJson: Int = getColumnIndexOrThrow(_stmt, "itemsJson")
        val _columnIndexOfSubtotal: Int = getColumnIndexOrThrow(_stmt, "subtotal")
        val _columnIndexOfShippingFee: Int = getColumnIndexOrThrow(_stmt, "shippingFee")
        val _columnIndexOfTotalAmount: Int = getColumnIndexOrThrow(_stmt, "totalAmount")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfVaNumber: Int = getColumnIndexOrThrow(_stmt, "vaNumber")
        val _columnIndexOfShippingAddress: Int = getColumnIndexOrThrow(_stmt, "shippingAddress")
        val _columnIndexOfRecipientName: Int = getColumnIndexOrThrow(_stmt, "recipientName")
        val _columnIndexOfRecipientPhone: Int = getColumnIndexOrThrow(_stmt, "recipientPhone")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<OrderEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: OrderEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpOrderNumber: String
          _tmpOrderNumber = _stmt.getText(_columnIndexOfOrderNumber)
          val _tmpItemsJson: String
          _tmpItemsJson = _stmt.getText(_columnIndexOfItemsJson)
          val _tmpSubtotal: Long
          _tmpSubtotal = _stmt.getLong(_columnIndexOfSubtotal)
          val _tmpShippingFee: Long
          _tmpShippingFee = _stmt.getLong(_columnIndexOfShippingFee)
          val _tmpTotalAmount: Long
          _tmpTotalAmount = _stmt.getLong(_columnIndexOfTotalAmount)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpPaymentMethod: String
          _tmpPaymentMethod = _stmt.getText(_columnIndexOfPaymentMethod)
          val _tmpVaNumber: String
          _tmpVaNumber = _stmt.getText(_columnIndexOfVaNumber)
          val _tmpShippingAddress: String
          _tmpShippingAddress = _stmt.getText(_columnIndexOfShippingAddress)
          val _tmpRecipientName: String
          _tmpRecipientName = _stmt.getText(_columnIndexOfRecipientName)
          val _tmpRecipientPhone: String
          _tmpRecipientPhone = _stmt.getText(_columnIndexOfRecipientPhone)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = OrderEntity(_tmpId,_tmpOrderNumber,_tmpItemsJson,_tmpSubtotal,_tmpShippingFee,_tmpTotalAmount,_tmpStatus,_tmpPaymentMethod,_tmpVaNumber,_tmpShippingAddress,_tmpRecipientName,_tmpRecipientPhone,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: String): OrderEntity? {
    val _sql: String = "SELECT * FROM orders WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfOrderNumber: Int = getColumnIndexOrThrow(_stmt, "orderNumber")
        val _columnIndexOfItemsJson: Int = getColumnIndexOrThrow(_stmt, "itemsJson")
        val _columnIndexOfSubtotal: Int = getColumnIndexOrThrow(_stmt, "subtotal")
        val _columnIndexOfShippingFee: Int = getColumnIndexOrThrow(_stmt, "shippingFee")
        val _columnIndexOfTotalAmount: Int = getColumnIndexOrThrow(_stmt, "totalAmount")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfVaNumber: Int = getColumnIndexOrThrow(_stmt, "vaNumber")
        val _columnIndexOfShippingAddress: Int = getColumnIndexOrThrow(_stmt, "shippingAddress")
        val _columnIndexOfRecipientName: Int = getColumnIndexOrThrow(_stmt, "recipientName")
        val _columnIndexOfRecipientPhone: Int = getColumnIndexOrThrow(_stmt, "recipientPhone")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: OrderEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpOrderNumber: String
          _tmpOrderNumber = _stmt.getText(_columnIndexOfOrderNumber)
          val _tmpItemsJson: String
          _tmpItemsJson = _stmt.getText(_columnIndexOfItemsJson)
          val _tmpSubtotal: Long
          _tmpSubtotal = _stmt.getLong(_columnIndexOfSubtotal)
          val _tmpShippingFee: Long
          _tmpShippingFee = _stmt.getLong(_columnIndexOfShippingFee)
          val _tmpTotalAmount: Long
          _tmpTotalAmount = _stmt.getLong(_columnIndexOfTotalAmount)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpPaymentMethod: String
          _tmpPaymentMethod = _stmt.getText(_columnIndexOfPaymentMethod)
          val _tmpVaNumber: String
          _tmpVaNumber = _stmt.getText(_columnIndexOfVaNumber)
          val _tmpShippingAddress: String
          _tmpShippingAddress = _stmt.getText(_columnIndexOfShippingAddress)
          val _tmpRecipientName: String
          _tmpRecipientName = _stmt.getText(_columnIndexOfRecipientName)
          val _tmpRecipientPhone: String
          _tmpRecipientPhone = _stmt.getText(_columnIndexOfRecipientPhone)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _result = OrderEntity(_tmpId,_tmpOrderNumber,_tmpItemsJson,_tmpSubtotal,_tmpShippingFee,_tmpTotalAmount,_tmpStatus,_tmpPaymentMethod,_tmpVaNumber,_tmpShippingAddress,_tmpRecipientName,_tmpRecipientPhone,_tmpCreatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getByOrderNumber(orderNumber: String): OrderEntity? {
    val _sql: String = "SELECT * FROM orders WHERE orderNumber = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, orderNumber)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfOrderNumber: Int = getColumnIndexOrThrow(_stmt, "orderNumber")
        val _columnIndexOfItemsJson: Int = getColumnIndexOrThrow(_stmt, "itemsJson")
        val _columnIndexOfSubtotal: Int = getColumnIndexOrThrow(_stmt, "subtotal")
        val _columnIndexOfShippingFee: Int = getColumnIndexOrThrow(_stmt, "shippingFee")
        val _columnIndexOfTotalAmount: Int = getColumnIndexOrThrow(_stmt, "totalAmount")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfVaNumber: Int = getColumnIndexOrThrow(_stmt, "vaNumber")
        val _columnIndexOfShippingAddress: Int = getColumnIndexOrThrow(_stmt, "shippingAddress")
        val _columnIndexOfRecipientName: Int = getColumnIndexOrThrow(_stmt, "recipientName")
        val _columnIndexOfRecipientPhone: Int = getColumnIndexOrThrow(_stmt, "recipientPhone")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: OrderEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpOrderNumber: String
          _tmpOrderNumber = _stmt.getText(_columnIndexOfOrderNumber)
          val _tmpItemsJson: String
          _tmpItemsJson = _stmt.getText(_columnIndexOfItemsJson)
          val _tmpSubtotal: Long
          _tmpSubtotal = _stmt.getLong(_columnIndexOfSubtotal)
          val _tmpShippingFee: Long
          _tmpShippingFee = _stmt.getLong(_columnIndexOfShippingFee)
          val _tmpTotalAmount: Long
          _tmpTotalAmount = _stmt.getLong(_columnIndexOfTotalAmount)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpPaymentMethod: String
          _tmpPaymentMethod = _stmt.getText(_columnIndexOfPaymentMethod)
          val _tmpVaNumber: String
          _tmpVaNumber = _stmt.getText(_columnIndexOfVaNumber)
          val _tmpShippingAddress: String
          _tmpShippingAddress = _stmt.getText(_columnIndexOfShippingAddress)
          val _tmpRecipientName: String
          _tmpRecipientName = _stmt.getText(_columnIndexOfRecipientName)
          val _tmpRecipientPhone: String
          _tmpRecipientPhone = _stmt.getText(_columnIndexOfRecipientPhone)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _result = OrderEntity(_tmpId,_tmpOrderNumber,_tmpItemsJson,_tmpSubtotal,_tmpShippingFee,_tmpTotalAmount,_tmpStatus,_tmpPaymentMethod,_tmpVaNumber,_tmpShippingAddress,_tmpRecipientName,_tmpRecipientPhone,_tmpCreatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateStatus(id: String, status: String) {
    val _sql: String = "UPDATE orders SET status = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, status)
        _argIndex = 2
        _stmt.bindText(_argIndex, id)
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
