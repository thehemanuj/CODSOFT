package ay.building.nudge

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id:Int,
    viewModel:NudgeViewModel,
    navController: NavController
)
{
    val snackMessage=remember{
        mutableStateOf("")
    }

    val scope=rememberCoroutineScope()
    val scaffoldState=rememberScaffoldState()
    if(id!=0){
        val nudge=viewModel.getNudge(id).collectAsState(initial = Nudge(id=0,"","",false))
        viewModel.nudgeTitleState=nudge.value.title
        viewModel.nudgeDescriptionState=nudge.value.description?:""
        viewModel.completionState=nudge.value.completed
    }else{
        viewModel.nudgeTitleState=""
        viewModel.nudgeDescriptionState=""
        viewModel.completionState=false
    }

    Scaffold(
        scaffoldState=scaffoldState,
        modifier=Modifier.padding(top=40.dp),
        topBar={
            AppBar(
                if(id==0) "Add Nudge" else "Update Nudge"
            ){
                navController.navigateUp()
            }
        },

    ){
        Image(
            painter=rememberAsyncImagePainter(model=R.drawable.dood1),
            contentDescription = null,
            contentScale= ContentScale.Crop,
            modifier=Modifier.fillMaxWidth()
        )
        Column(
            modifier= Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            Spacer(modifier=Modifier.height(10.dp))
            NudgeTextField(value = viewModel.nudgeTitleState, label="Title" ,{viewModel.onNudgeTitleChange(it)})
            Spacer(modifier=Modifier.height(10.dp))
            NudgeTextField(value = viewModel.nudgeDescriptionState, label ="Description",{viewModel.onNudgeDescriptionChange(it)} )
            Spacer(modifier=Modifier.height(10.dp))
            Button(colors=ButtonColors(contentColor=Color.White, disabledContainerColor = Color(60,66,77),disabledContentColor=Color.White,containerColor = Color(60,66,77)),onClick={
                if(viewModel.nudgeTitleState.isNotEmpty()){
                    if(id==0){
                        viewModel.addNudge(
                            Nudge(
                                title=viewModel.nudgeTitleState,
                                description=viewModel.nudgeDescriptionState,
                                completed=viewModel.completionState
                            )
                        )
                        snackMessage.value="Nudge Added Successfully"
                    }
                    else{
                        viewModel.updateNudge(
                            Nudge(
                                id=id,
                                title=viewModel.nudgeTitleState,
                                description=viewModel.nudgeDescriptionState
                            )
                        )
                        snackMessage.value="Nudge Updated Successfully"
                    }
                }else{
                    snackMessage.value="Enter Title(Atleast) to create a Nudge"
                }
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigateUp()
                }
            }
            ){
                Text(if(id==0) "Add" else "Update",fontSize=18.sp)
            }
        }
    }
}

@Composable
fun NudgeTextField(value:String,
                  label:String,
                  onValueChanged:(String)->Unit){
    OutlinedTextField(value =value , onValueChange =onValueChanged,
        label={ Text(label,color= Color.Black) },
        modifier=Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
        ),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.Black,
            focusedTextColor = Color(60,66,77),
            cursorColor = Color(60,66,77),
            focusedContainerColor = Color.Transparent,
            focusedBorderColor = Color(60,66,77)
        ))

}