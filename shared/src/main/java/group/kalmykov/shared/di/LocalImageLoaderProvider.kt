package group.kalmykov.shared.di


import androidx.compose.runtime.compositionLocalOf
import coil.ImageLoader

val LocalImageLoader = compositionLocalOf<ImageLoader> {
    error("ImageLoader not provided")
}