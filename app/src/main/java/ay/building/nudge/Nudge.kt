package ay.building.nudge

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="nudge-table" )
data class Nudge(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    @ColumnInfo(name="nudge-title")
    val title:String="",
    @ColumnInfo(name="nudge-description")
    val description:String?="",
    @ColumnInfo(name="complete-status")
    val completed:Boolean=false
)