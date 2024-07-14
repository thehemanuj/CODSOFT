package ay.building.nudge

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(viewModel:NudgeViewModel,
             navController: NavController,){

    Scaffold(
        modifier= Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
            topBar={AppBar(title="Nudge", { navController.navigate("home_screen") })},
        floatingActionButton = {
            FloatingActionButton(
                modifier=Modifier.padding(all=20.dp),
                contentColor = Color.White,
                backgroundColor=Color(60,66,77),
                onClick =
                {
                    navController.navigate(Screen.Add.route+"/0")
                }
            )
            {
                Icon(
                    imageVector= Icons.Default.Add,
                    contentDescription = null,
                    tint= Color.White
                )
            }
        }
    ){
        Image(painter= painterResource(id=R.drawable.imggg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier=Modifier.fillMaxSize())

        val nudgeList=viewModel.getAllNudges.collectAsState(initial =listOf())
        LazyColumn(
            modifier= Modifier
                .padding(it)
                .fillMaxSize()
        )
        {
            items(nudgeList.value,key={wish->wish.id}) { nudge ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            viewModel.deleteNudge(nudge)
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = dismissState, background = {
                        val color by animateColorAsState(
                            if (dismissState.dismissDirection == DismissDirection.EndToStart) Color.Red else Color.Transparent,
                            label = ""
                        )

                        val alignment = Alignment.CenterEnd

                        Box(
                            Modifier
                                .fillMaxSize(0.75f)
                                .fillMaxWidth()
                                .background(color)
                                .padding(horizontal=20.dp)
                                .align(alignment = Alignment.CenterVertically)
                                ,
                            contentAlignment = alignment,

                        ) {
                            Icon(
                                Icons.Default.Delete, tint = Color.White,
                                contentDescription = null
                            )
                        }
                    },

                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(1f) },
                    dismissContent = {
                        NudgeItem(nudge,viewModel=viewModel) {
                            val id = nudge.id
                            navController.navigate(Screen.Add.route + "/$id")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NudgeItem(nudge: Nudge, viewModel:NudgeViewModel, onClick:() -> Unit  ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
        contentColor = Color.Blue
    ) {
        Box(
            Modifier.fillMaxSize(),
        ) {

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    nudge.title, fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp, color = Color(60,66,77), fontStyle = FontStyle.Italic
                )

                Text(nudge.description ?: "", color = Color(60,66,77), fontSize = 15.sp)

                Row(modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Checkbox(checked = nudge.completed, onCheckedChange = {
                        if (viewModel.completionState == false) {
                            viewModel.completionState = true
                            viewModel.updateNudge(Nudge(
                                id=nudge.id,
                                title=nudge.title,
                                description=nudge.description,
                                completed = true
                            ))
                        } else {
                            viewModel.completionState = false
                            viewModel.updateNudge(Nudge(
                                id=nudge.id,
                                title=nudge.title,
                                description=nudge.description,
                                completed = false
                            ))
                        }

                    })

                    var text:String=
                        if(nudge.completed==true) "Completed" else "Pending"

                    val style= TextStyle(
                        color= if(text=="Completed") Color.Green else Color.Yellow
                        , fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Text(
                        text,style=style
                    )
                }
            }
        }
    }
}