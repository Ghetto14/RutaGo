package com.ghettodev.rutago.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// IMPORTA LO DE USUARIOS
import com.ghettodev.rutago.data.dao.UsuarioDao
import com.ghettodev.rutago.data.entity.Usuario

// LO QUE YA TENÍAS
import com.ghettodev.rutago.data.dao.RutaDao
import com.ghettodev.rutago.data.dao.ParadaDao
import com.ghettodev.rutago.data.dao.RutaParadaDao
import com.ghettodev.rutago.data.entity.Ruta
import com.ghettodev.rutago.data.entity.Parada
import com.ghettodev.rutago.data.entity.RutaParada

@Database(
    entities = [
        Usuario::class,      // ← AGREGAR AQUÍ
        Ruta::class,
        Parada::class,
        RutaParada::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao        // ← AGREGAR AQUÍ
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
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}