package one.vitaliy.mygaragesample.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.hideFromAccessibility
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import coil3.compose.AsyncImage
import one.vitaliy.mygaragesample.MainUiState
import one.vitaliy.mygaragesample.MainViewModel
import one.vitaliy.mygaragesample.api.CarDomain
import one.vitaliy.mygaragesample.details.VehicleDetails
import vwg.skoda.maulcompose.lib.components.MaulCircularProgressIndicator
import vwg.skoda.maulcompose.lib.components.MaulHorizontalDivider
import vwg.skoda.maulcompose.lib.components.MaulText
import vwg.skoda.maulcompose.lib.foundation.MaulTheme

internal const val VEHICLE_LIST_GRAPH_ROUTE = "vehicle_list_graph"
internal const val VEHICLE_LIST_ROUTE = "vehicle_list"

internal fun NavGraphBuilder.vehicleList(
    viewModel: MainViewModel,
    onVehicleDetailsClicked: (CarDomain) -> Unit,
) {
    navigation(startDestination = VEHICLE_LIST_ROUTE, route = VEHICLE_LIST_GRAPH_ROUTE) {
        composable(VEHICLE_LIST_ROUTE) {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            VehicleList(uiState, onVehicleDetailsClicked, onRefresh = viewModel::onRefresh)
        }
        composable<CarDomain> {
            val vehicle = it.toRoute<CarDomain>()
            VehicleDetails(vehicle)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun VehicleList(
    uiState: MainUiState,
    onVehicleDetailsClicked: (CarDomain) -> Unit,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
) {
    PullToRefreshBox(
        isRefreshing = (uiState as? MainUiState.Content)?.isRefreshing == true,
        onRefresh = onRefresh,
        modifier = modifier.fillMaxSize()
    ) {
        AnimatedContent(uiState) {
            when (it) {
                is MainUiState.Content -> LazyColumn(
                    modifier = Modifier.padding(vertical = MaulTheme.dimensions.spaceS),
                    verticalArrangement = Arrangement.spacedBy(MaulTheme.dimensions.spaceM)
                ) {
                    itemsIndexed(it.carList, key = { _, car -> car.name }) { index, car ->
                        Column {
                            Row(
                                modifier = Modifier
                                    .clickable { onVehicleDetailsClicked(car) }
                                    .fillMaxWidth()
                                    .padding(horizontal = MaulTheme.dimensions.spaceM),
                                horizontalArrangement = Arrangement.spacedBy(MaulTheme.dimensions.spaceM),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                AsyncImage(
                                    car.listImageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .semantics { hideFromAccessibility() }
                                        .weight(0.4f)
                                )
                                MaulText(
                                    text = AnnotatedString(car.name),
                                    modifier = Modifier
                                        .weight(0.6f)
                                        .fillMaxWidth(),
                                )
                            }
                            MaulHorizontalDivider(modifier = Modifier.fillMaxWidth())
                        }
                    }
                }

                MainUiState.Error -> {
                    // TODO an error screen goes here
                }

                MainUiState.Loading -> MaulCircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
        }
    }
}