package ay.building.nudge

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.material.TopAppBar
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppBar(
    title:String,
    onBackClick:()->Unit
)
{
    val navigationIcon:  (@Composable ()->Unit) ? =
        if(title.contains("Add")||title.contains("Update")){
            {
                IconButton(onClick={onBackClick()}){
                    Icon(
                        imageVector= Icons.AutoMirrored.Filled.ArrowBack,
                        tint= Color.White,
                        contentDescription = null
                    )
                }
            }
        }else null

    val style= TextStyle(
        color=Color.White,
        fontWeight = FontWeight.Bold,
        fontSize=25.sp,
        fontStyle = FontStyle.Normal,
        fontFamily = FontFamily.Cursive
    )


    TopAppBar(
        title = {
            Text(text = title,
                style=style,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 40.dp))
        },
        elevation = 3.dp,
        backgroundColor = Color(60,66,77),
        navigationIcon = navigationIcon
    )

}