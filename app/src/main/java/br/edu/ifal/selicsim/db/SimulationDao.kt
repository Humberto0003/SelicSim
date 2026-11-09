package br.edu.ifal.selicsim.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.edu.ifal.selicsim.domain.SimulationEntity

@Dao
interface SimulationDao {

    @Insert
    suspend fun insert(simulation: SimulationEntity)

    @Query("SELECT * FROM simulations ORDER BY createdAt DESC")
    suspend fun findAll(): List<SimulationEntity>
}