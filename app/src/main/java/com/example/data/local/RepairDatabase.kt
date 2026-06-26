package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [RepairNote::class, CompletedLesson::class, QuizScore::class],
    version = 1,
    exportSchema = false
)
abstract class RepairDatabase : RoomDatabase() {
    abstract fun repairDao(): RepairDao

    companion object {
        @Volatile
        private var INSTANCE: RepairDatabase? = null

        fun getDatabase(context: Context): RepairDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RepairDatabase::class.java,
                    "repair_master_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
