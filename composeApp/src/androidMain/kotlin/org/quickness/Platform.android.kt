@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.quickness

import android.content.Intent
import android.net.Uri
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
import org.quickness.utils.ContextProvider

actual class Uri actual constructor(url: String) : org.quickness.interfaces.plataform.Uri {
    private val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    actual override fun navigate() {
        ContextProvider.getContext()!!.startActivity(intent)
    }
}

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
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = LatLng(19.475426, -102.072805)),
                title = "TecNm"
            )
        }
    }
}