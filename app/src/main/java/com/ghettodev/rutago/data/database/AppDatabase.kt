package com.ghettodev.rutago.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.ghettodev.rutago.data.dao.RutaDao
import com.ghettodev.rutago.data.dao.ParadaDao
import com.ghettodev.rutago.data.dao.RutaParadaDao
import com.ghettodev.rutago.data.entity.Ruta
import com.ghettodev.rutago.data.entity.Parada
import com.ghettodev.rutago.data.entity.RutaParada

@Database(
    entities = [Ruta::class, Parada::class, RutaParada::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rutaDao(): RutaDao
    abstract fun paradaDao(): ParadaDao
    abstract fun rutaParadaDao(): RutaParadaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rutago_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}