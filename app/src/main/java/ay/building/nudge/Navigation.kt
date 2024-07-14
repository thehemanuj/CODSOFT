package ay.building.nudge

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@Composable
fun Navigation(modifier: Modifier, viewModel:NudgeViewModel,
               navController: NavHostController= rememberNavController()){

    NavHost(navController = navController,
        startDestination = Screen.Home.route){

        composable(Screen.Home.route){
            HomeView(viewModel =viewModel,navController=navController)
        }

        composable(Screen.Add.route+"/{id}",arguments=
        listOf(
            navArgument("id"){
                type= NavType.IntType
                defaultValue=0
                nullable=false
            }
        )){
            val id=if(it.arguments!=null) it.arguments!!.getInt("id") else 0
            AddEditDetailView(id=id,viewModel=viewModel,navController=navController)
        }
    }
}