package com.lennartmoeller.ma.composemultiplatform.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lennartmoeller.ma.composemultiplatform.database.Database
import com.lennartmoeller.ma.composemultiplatform.utility.Font

@Composable
fun FontAwesomeIcon(
    unicode: String? = null,
    name: String? = null,
    style: Font = SolidStyle(),
    size: TextUnit = 20.sp,
    opacity: Float = 1f
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
        modifier = Modifier.size(with(LocalDensity.current) { size.toPx() }.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = unicodeStr,
            style = TextStyle(
                fontFamily = FontFamily(style),
                fontSize = size
            ),
            maxLines = 1,
            overflow = TextOverflow.Visible,
            modifier = Modifier.alpha(realOpacity)
        )
    }
}

@Composable
fun BrandsStyle(): Font {
    return Font("fa-brands", "fa-brands-400", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun DuotoneStyle(): Font {
    return Font("fa-duotone", "fa-duotone-900", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun LightStyle(): Font {
    return Font("fa-light", "fa-light-300", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun RegularStyle(): Font {
    return Font("fa-regular", "fa-regular-400", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun SharpLightStyle(): Font {
    return Font("fa-sharp-light", "fa-sharp-light-300", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun SharpRegularStyle(): Font {
    return Font("fa-sharp-regular", "fa-sharp-regular-400", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun SharpSolidStyle(): Font {
    return Font("fa-sharp-solid", "fa-sharp-solid-900", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun SolidStyle(): Font {
    return Font("fa-solid", "fa-solid-900", FontWeight.Normal, FontStyle.Normal)
}

@Composable
fun ThinStyle(): Font {
    return Font("fa-thin", "fa-thin-100", FontWeight.Normal, FontStyle.Normal)
}