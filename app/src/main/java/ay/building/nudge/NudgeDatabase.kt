package ay.building.nudge

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities=[Nudge::class],
    version=1,
    exportSchema=false
)
abstract class NudgeDatabase: RoomDatabase(){
    abstract fun nudgeDao():NudgeDao
}