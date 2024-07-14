package ay.building.nudge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NudgeViewModel(
    private val nudgeRep:NudgeRepo=Graph.nudgeRepo
): ViewModel() {

    var nudgeTitleState by mutableStateOf("")
    var nudgeDescriptionState by mutableStateOf("")
    var completionState by mutableStateOf(false)

    fun onNudgeTitleChange(it:String){
        nudgeTitleState=it
    }

    fun onNudgeDescriptionChange(it:String){
        nudgeDescriptionState=it
    }

    fun onCompletionChange(it:Boolean){
        completionState=it
    }

    lateinit var getAllNudges: Flow<List<Nudge>>

    init{
        viewModelScope.launch{
           getAllNudges=nudgeRep.getAllNudges()
        }
    }

    fun addNudge(nudge:Nudge){
        viewModelScope.launch{
            nudgeRep.insertANudge(nudge)
        }
    }

    fun updateNudge(nudge:Nudge){
        viewModelScope.launch(Dispatchers.IO){
            nudgeRep.updateANudge(nudge)
        }
    }

    fun deleteNudge(nudge:Nudge){
        viewModelScope.launch(Dispatchers.IO){
            nudgeRep.deleteANudge(nudge)
        }
    }

    fun getNudge(id:Int):Flow<Nudge>{
       return nudgeRep.getANudge(id)
    }
}