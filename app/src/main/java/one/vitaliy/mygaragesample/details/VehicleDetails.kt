package one.vitaliy.mygaragesample.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import one.vitaliy.mygaragesample.api.CarDomain
import one.vitaliy.mygaragesample.api.getVehicleDetails
import vwg.skoda.maulcompose.lib.components.MaulLazyList
import vwg.skoda.maulcompose.lib.foundation.MaulTheme

@Composable
internal fun VehicleDetails(
    vehicle: CarDomain,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaulTheme.dimensions.spaceL),
    ) {
        AsyncImage(
            model = vehicle.detailImageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        MaulLazyList(
            modifier = modifier,
            rows = vehicle.getVehicleDetails(),
        )
    }
}