package com.shopika.app.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.shopika.app.`data`.local.dao.CartDao
import com.shopika.app.`data`.local.dao.CartDao_Impl
import com.shopika.app.`data`.local.dao.FavoriteDao
import com.shopika.app.`data`.local.dao.FavoriteDao_Impl
import com.shopika.app.`data`.local.dao.OrderDao
import com.shopika.app.`data`.local.dao.OrderDao_Impl
import com.shopika.app.`data`.local.dao.ProductDao
import com.shopika.app.`data`.local.dao.ProductDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ShopikaDatabase_Impl : ShopikaDatabase() {
  private val _productDao: Lazy<ProductDao> = lazy {
    ProductDao_Impl(this)
  }

  private val _cartDao: Lazy<CartDao> = lazy {
    CartDao_Impl(this)
  }

  private val _orderDao: Lazy<OrderDao> = lazy {
    OrderDao_Impl(this)
  }

  private val _favoriteDao: Lazy<FavoriteDao> = lazy {
    FavoriteDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "29a483c6c44bff8752982d01f0d6053a", "54eb540f904decc2870db0b46ed4f974") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `products` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `price` INTEGER NOT NULL, `originalPrice` INTEGER, `discountPercent` INTEGER NOT NULL, `category` TEXT NOT NULL, `imageUrl` TEXT NOT NULL, `rating` REAL NOT NULL, `soldCount` INTEGER NOT NULL, `stock` INTEGER NOT NULL, `variantsCsv` TEXT NOT NULL, `isPopular` INTEGER NOT NULL, `isRecommended` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `cart_items` (`id` TEXT NOT NULL, `productId` TEXT NOT NULL, `productName` TEXT NOT NULL, `productPrice` INTEGER NOT NULL, `productImage` TEXT NOT NULL, `variant` TEXT NOT NULL, `quantity` INTEGER NOT NULL, `stock` INTEGER NOT NULL, `isSelected` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `orders` (`id` TEXT NOT NULL, `orderNumber` TEXT NOT NULL, `itemsJson` TEXT NOT NULL, `subtotal` INTEGER NOT NULL, `shippingFee` INTEGER NOT NULL, `totalAmount` INTEGER NOT NULL, `status` TEXT NOT NULL, `paymentMethod` TEXT NOT NULL, `vaNumber` TEXT NOT NULL, `shippingAddress` TEXT NOT NULL, `recipientName` TEXT NOT NULL, `recipientPhone` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `favorites` (`productId` TEXT NOT NULL, `addedAt` INTEGER NOT NULL, PRIMARY KEY(`productId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '29a483c6c44bff8752982d01f0d6053a')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `products`")
        connection.execSQL("DROP TABLE IF EXISTS `cart_items`")
        connection.execSQL("DROP TABLE IF EXISTS `orders`")
        connection.execSQL("DROP TABLE IF EXISTS `favorites`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsProducts: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsProducts.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("description", TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("price", TableInfo.Column("price", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("originalPrice", TableInfo.Column("originalPrice", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("discountPercent", TableInfo.Column("discountPercent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("category", TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("imageUrl", TableInfo.Column("imageUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("rating", TableInfo.Column("rating", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("soldCount", TableInfo.Column("soldCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("stock", TableInfo.Column("stock", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("variantsCsv", TableInfo.Column("variantsCsv", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("isPopular", TableInfo.Column("isPopular", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("isRecommended", TableInfo.Column("isRecommended", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysProducts: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesProducts: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoProducts: TableInfo = TableInfo("products", _columnsProducts, _foreignKeysProducts, _indicesProducts)
        val _existingProducts: TableInfo = read(connection, "products")
        if (!_infoProducts.equals(_existingProducts)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |products(com.shopika.app.data.local.entity.ProductEntity).
              | Expected:
              |""".trimMargin() + _infoProducts + """
              |
              | Found:
              |""".trimMargin() + _existingProducts)
        }
        val _columnsCartItems: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsCartItems.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("productId", TableInfo.Column("productId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("productName", TableInfo.Column("productName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("productPrice", TableInfo.Column("productPrice", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("productImage", TableInfo.Column("productImage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("variant", TableInfo.Column("variant", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("quantity", TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("stock", TableInfo.Column("stock", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("isSelected", TableInfo.Column("isSelected", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysCartItems: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesCartItems: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoCartItems: TableInfo = TableInfo("cart_items", _columnsCartItems, _foreignKeysCartItems, _indicesCartItems)
        val _existingCartItems: TableInfo = read(connection, "cart_items")
        if (!_infoCartItems.equals(_existingCartItems)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |cart_items(com.shopika.app.data.local.entity.CartEntity).
              | Expected:
              |""".trimMargin() + _infoCartItems + """
              |
              | Found:
              |""".trimMargin() + _existingCartItems)
        }
        val _columnsOrders: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsOrders.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("orderNumber", TableInfo.Column("orderNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("itemsJson", TableInfo.Column("itemsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("subtotal", TableInfo.Column("subtotal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("shippingFee", TableInfo.Column("shippingFee", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("totalAmount", TableInfo.Column("totalAmount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("status", TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("paymentMethod", TableInfo.Column("paymentMethod", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("vaNumber", TableInfo.Column("vaNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("shippingAddress", TableInfo.Column("shippingAddress", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("recipientName", TableInfo.Column("recipientName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("recipientPhone", TableInfo.Column("recipientPhone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysOrders: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesOrders: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoOrders: TableInfo = TableInfo("orders", _columnsOrders, _foreignKeysOrders, _indicesOrders)
        val _existingOrders: TableInfo = read(connection, "orders")
        if (!_infoOrders.equals(_existingOrders)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |orders(com.shopika.app.data.local.entity.OrderEntity).
              | Expected:
              |""".trimMargin() + _infoOrders + """
              |
              | Found:
              |""".trimMargin() + _existingOrders)
        }
        val _columnsFavorites: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsFavorites.put("productId", TableInfo.Column("productId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFavorites.put("addedAt", TableInfo.Column("addedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysFavorites: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesFavorites: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoFavorites: TableInfo = TableInfo("favorites", _columnsFavorites, _foreignKeysFavorites, _indicesFavorites)
        val _existingFavorites: TableInfo = read(connection, "favorites")
        if (!_infoFavorites.equals(_existingFavorites)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |favorites(com.shopika.app.data.local.entity.FavoriteEntity).
              | Expected:
              |""".trimMargin() + _infoFavorites + """
              |
              | Found:
              |""".trimMargin() + _existingFavorites)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "products", "cart_items", "orders", "favorites")
  }

  public override fun clearAllTables() {
    super.performClear(false, "products", "cart_items", "orders", "favorites")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(ProductDao::class, ProductDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(CartDao::class, CartDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(OrderDao::class, OrderDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(FavoriteDao::class, FavoriteDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun productDao(): ProductDao = _productDao.value

  public override fun cartDao(): CartDao = _cartDao.value

  public override fun orderDao(): OrderDao = _orderDao.value

  public override fun favoriteDao(): FavoriteDao = _favoriteDao.value
}
