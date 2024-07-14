package ay.building.nudge

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
abstract class NudgeDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    abstract suspend fun insertNudge(nudgeEntity:Nudge)

    @Delete
    abstract suspend fun deleteNudge(nudgeEntity:Nudge)

    @Query("SELECT * FROM `nudge-table`")
    abstract fun getAllNudges(): Flow<List<Nudge>>

    @Query("SELECT * FROM `nudge-table` WHERE id=:id")
    abstract fun getNudgeById(id:Int):Flow<Nudge>

    @Update
    abstract suspend fun updateNudge(nudgeEntity:Nudge)

}