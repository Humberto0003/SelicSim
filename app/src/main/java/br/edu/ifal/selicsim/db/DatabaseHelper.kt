package br.edu.ifal.selicsim.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.ifal.selicsim.domain.SimulationEntity

@Database(
    entities = [SimulationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun simulationDao(): SimulationDao

    companion object {
        fun getInstance(context: Context): DatabaseHelper {
            return Room.databaseBuilder(
                context,
                DatabaseHelper::class.java,
                "selicsim.db"
            ).build()
        }
    }
}