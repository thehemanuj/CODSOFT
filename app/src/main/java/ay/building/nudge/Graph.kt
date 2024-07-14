package ay.building.nudge

import android.content.Context
import androidx.room.Room

object Graph {
    lateinit var database: NudgeDatabase

    val nudgeRepo by lazy {
        NudgeRepo(database.nudgeDao())
    }

    fun provide(context:Context){
        database = Room.databaseBuilder(context, NudgeDatabase::class.java,"nudge-database.db").build()
    }
}