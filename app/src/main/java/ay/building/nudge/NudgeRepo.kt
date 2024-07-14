package ay.building.nudge

import kotlinx.coroutines.flow.Flow

class NudgeRepo(private val nudgeDao: NudgeDao) {

    suspend fun insertANudge(nudge:Nudge) = nudgeDao.insertNudge(nudge)

    suspend fun deleteANudge(nudge:Nudge){
    nudgeDao.deleteNudge(nudge)
    }

    fun getAllNudges(): Flow<List<Nudge>> =nudgeDao.getAllNudges()

    fun getANudge(id:Int):Flow<Nudge> =nudgeDao.getNudgeById(id)

    suspend fun updateANudge(nudge:Nudge) = nudgeDao.updateNudge(nudge)
}