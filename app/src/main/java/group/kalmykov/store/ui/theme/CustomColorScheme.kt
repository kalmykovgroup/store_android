package group.kalmykov.store.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

val ColorScheme.cardImgBackground: Color
    @Composable
    @ReadOnlyComposable
    get() = AppColors.CardImgBackground

val ColorScheme.cardText: Color
    @Composable
    @ReadOnlyComposable
    get() = AppColors.CardText

val ColorScheme.cardTitle: Color
    @Composable
    @ReadOnlyComposable
    get() = AppColors.CardTitle

val ColorScheme.cardBlockBackground: Color
    @Composable
    @ReadOnlyComposable
    get() = AppColors.CardBlockBackground

val ColorScheme.cardPriceText: Color
    @Composable
    @ReadOnlyComposable
    get() = AppColors.CardPriceText

val ColorScheme.bottomNavSelected: Color
    @Composable
    @ReadOnlyComposable
    get() = AppColors.BottomNavSelected

val ColorScheme.bottomNavUnselected: Color
    @Composable
    @ReadOnlyComposable
    get() = AppColors.BottomNavUnselected