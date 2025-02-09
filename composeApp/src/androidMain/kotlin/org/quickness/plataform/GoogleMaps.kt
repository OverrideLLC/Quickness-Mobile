package org.quickness.plataform

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

actual class GoogleMaps actual constructor() {
    @Composable
    actual fun Map() {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                LatLng(19.475426, -102.072805),
                15f
            )
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(
                mapType = MapType.NORMAL,
                isMyLocationEnabled = true,
                isTrafficEnabled = true,
                isIndoorEnabled = true,
                isBuildingEnabled = true
            ),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = MarkerState(position = LatLng(19.475426, -102.072805)),
                title = "TecNm"
            )
        }
    }
}