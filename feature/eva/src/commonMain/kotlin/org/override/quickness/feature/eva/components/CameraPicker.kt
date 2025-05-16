package org.override.quickness.feature.eva.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import network.chaintech.cmpimagepickncrop.CMPImagePickNCropDialog
import network.chaintech.cmpimagepickncrop.imagecropper.ImageCropper
import network.chaintech.cmpimagepickncrop.imagecropper.rememberImageCropper
import network.chaintech.cmpimagepickncrop.utils.ImagePickerDialogStyle

@Composable
internal fun ImagePicker(
    openImagePickerState: Boolean,
    imageCropper: ImageCropper = rememberImageCropper(),
    selectedImage: (ImageBitmap) -> Unit,
    openImagePicker: (Boolean) -> Unit
) {
    CMPImagePickNCropDialog(
        imageCropper = imageCropper,
        openImagePicker = openImagePickerState,
        imagePickerDialogStyle = ImagePickerDialogStyle(
            title = "Choose from option",
            txtCamera = "From Camera",
            txtGallery = "From Gallery",
            txtCameraColor = Color.DarkGray,
            txtGalleryColor = Color.DarkGray,
            cameraIconTint = Color.DarkGray,
            galleryIconTint = Color.DarkGray,
            backgroundColor = Color.White
        ),
        autoZoom = true,
        imagePickerDialogHandler = { openImagePicker(it) },
        selectedImageCallback = { selectedImage(it) }
    )
}