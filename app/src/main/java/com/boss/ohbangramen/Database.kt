package com.boss.ohbangramen

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Database(entities = [MenuItemRoom::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}

@Entity
data class MenuItemRoom(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

@Dao
interface MenuItemDao{
    @Query("SELECT * FROM MenuItemRoom")
    fun getAll(): LiveData<List<MenuItemRoom>>

    @Query("SELECT * FROM menuitemroom WHERE title LIKE :searchPhrase AND (:category = '' OR LOWER(category) = LOWER(:category))")
    fun getFilteredMenuItems(searchPhrase: String, category: String): LiveData<List<MenuItemRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg menuItems: MenuItemRoom)

    @Query("DELETE FROM menuitemroom")
    fun clearAll(): Int

    @Query("SELECT (SELECT COUNT(*) FROM MenuItemRoom) == 0")
    fun isEmpty(): Boolean

    @Query("SELECT * FROM menuitemroom WHERE id = :itemId")
    fun getMenuItemById(itemId: Int): LiveData<List<MenuItemRoom>>
}