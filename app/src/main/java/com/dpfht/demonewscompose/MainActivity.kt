package com.dpfht.demonewscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dpfht.demonewscompose.framework.navigation.NavigationService
import com.dpfht.demonewscompose.framework.navigation.Screen.IntroBaseRoute
import com.dpfht.demonewscompose.ui.theme.DemoNewsComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var navigationService: NavigationService

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val appWindowTitle = "${stringResource(id = R.string.app_name)}${stringResource(id = R.string.running_mode)}"
      val navController = rememberNavController()

      DemoNewsComposeTheme {
        AppNavigation(navigationService = navigationService, navController = navController)

        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          NavHost(
            navController = navController,
            startDestination = IntroBaseRoute.route
          ) {
            introGraph()
            mainGraph(appWindowTitle)
          }
        }
      }
    }
  }
}
