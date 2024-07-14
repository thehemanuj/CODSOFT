package ay.building.nudge

sealed class Screen(val route:String) {
    object Home:Screen("home_Screen")
    object Add:Screen("add_screen")
}