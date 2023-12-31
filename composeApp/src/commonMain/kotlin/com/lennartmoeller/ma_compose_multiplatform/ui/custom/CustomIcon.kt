package com.lennartmoeller.ma_compose_multiplatform.ui.custom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.lennartmoeller.ma_compose_multiplatform.database.Database
import com.lennartmoeller.ma_compose_multiplatform.ui.util.Font

@Composable
fun CustomIcon(
    unicode: String? = null,
    name: String? = null,
    style: Font = SolidStyle(),
    size: TextUnit = 20.sp,
    opacity: Float = 1f,
    color: Color = Color.Unspecified,
) {
    // unicode is preferred over name
    // use question mark if both are null
    // if database can't find any unicode, use space
    val unicodeStrDirty = unicode ?: Database.getIconUnicode(name ?: "question") ?: "\u0020"
    val unicodeStr = if (unicodeStrDirty.length == 1) {
        // already a string with one character
        unicodeStrDirty
    } else {
        // translate unicode string to character
        unicodeStrDirty.toInt(radix = 16).toChar().toString()
    }
    // if no opacity is set and the icon is a question mark, set opacity to .5
    val realOpacity = if (opacity == 1f && unicodeStr == "\u003f") .5f else opacity
    // square box to make sure the icon is centered
    Box(
        // square all icons
        modifier = Modifier.size(with(LocalDensity.current) { (size * 4 / 3).toDp() }),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = unicodeStr,
            fontFamily = FontFamily(style),
            fontSize = size,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Visible,
            // overrides settings by parent widgets
            lineHeight = 0.sp,
            modifier = Modifier.alpha(realOpacity),
        )
    }
}

@Composable
fun BrandsStyle(): Font {
    return Font("fa_brands", "fa_brands_400", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun DuotoneStyle(): Font {
    return Font("fa_duotone", "fa_duotone_900", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun LightStyle(): Font {
    return Font("fa_light", "fa_light_300", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun RegularStyle(): Font {
    return Font("fa_regular", "fa_regular_400", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun SharpLightStyle(): Font {
    return Font("fa_sharp_light", "fa_sharp_light_300", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun SharpRegularStyle(): Font {
    return Font("fa_sharp_regular", "fa_sharp_regular_400", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun SharpSolidStyle(): Font {
    return Font("fa_sharp_solid", "fa_sharp_solid_900", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun SolidStyle(): Font {
    return Font("fa_solid", "fa_solid_900", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun ThinStyle(): Font {
    return Font("fa_thin", "fa_thin_100", FontWeight.Normal, FontStyle.Normal)
}
