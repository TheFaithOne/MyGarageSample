package one.vitaliy.mygaragesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import one.vitaliy.mygaragesample.list.VEHICLE_LIST_GRAPH_ROUTE
import one.vitaliy.mygaragesample.list.vehicleList
import vwg.skoda.maulcompose.lib.components.MaulToolbar
import vwg.skoda.maulcompose.lib.foundation.MaulTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: MainViewModel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaulTheme {
                MainContent(viewModel)
            }
        }
    }
}

@Composable
private fun MainContent(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MaulToolbar(
                title = "My Garage sample",
                modifier = Modifier.statusBarsPadding(),
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = VEHICLE_LIST_GRAPH_ROUTE,
            modifier = Modifier.padding(innerPadding),
        ) {
            vehicleList(
                viewModel = viewModel,
                onVehicleDetailsClicked = { navController.navigate(it) })
        }
    }
}