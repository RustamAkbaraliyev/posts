package uz.xsoft.blog.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.xsoft.blog.data.source.local.daos.PostDao
import uz.xsoft.blog.data.source.local.entities.PostEntity

@Database(entities = [PostEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

}