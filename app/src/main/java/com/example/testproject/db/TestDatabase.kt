package com.example.testproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testproject.helpers.room.Converters

@Database(
    entities = [
        UserEntity::class,
        UserEntity.AvatarsEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class TestDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}