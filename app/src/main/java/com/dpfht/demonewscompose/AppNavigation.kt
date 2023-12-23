package com.dpfht.demonewscompose

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dpfht.demonewscompose.R.string
import com.dpfht.demonewscompose.feature_article.ArticleScreen
import com.dpfht.demonewscompose.feature_article_list.ArticleListScreen
import com.dpfht.demonewscompose.feature_category.CategoryScreen
import com.dpfht.demonewscompose.feature_error_message.AppBottomSheet
import com.dpfht.demonewscompose.feature_source.SourceScreen
import com.dpfht.demonewscompose.feature_splash.SplashScreen
import com.dpfht.demonewscompose.framework.navigation.NavigationService
import com.dpfht.demonewscompose.framework.navigation.Screen
import com.dpfht.demonewscompose.framework.navigation.Screen.IntroBaseRoute
import com.dpfht.demonewscompose.framework.navigation.Screen.None

@Composable
fun AppNavigation(navigationService: NavigationService, navController: NavHostController) {
  var showSheet by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf("") }

  if (showSheet) {
    AppBottomSheet(
      msg = errorMessage
    ) {
      navigationService.navigate(None)
      showSheet = false
    }
  }

  navigationService.screen.collectAsState().value.also { screen ->
    when (screen) {
      Screen.NavigateUp -> {
        navController.navigateUp()
        navigationService.navigate(None)
      }
      Screen.Category -> {
        navController.navigate(Screen.MainBaseRoute.route) {
          popUpTo(IntroBaseRoute.route)
        }
      }
      is Screen.Error -> {
        errorMessage = screen.message
        showSheet = true
        navigationService.navigate(None)
      }
      is Screen.Source -> {
        navController.navigate(screen.createRoute())
        navigationService.navigate(None)
      }
      is Screen.ArticleList -> {
        navController.navigate(screen.createRoute())
        navigationService.navigate(None)
      }
      is Screen.Article -> {
        navController.navigate(screen.createRoute())
        navigationService.navigate(None)
      }
      else -> {
        if (screen.route.isNotEmpty()) {
          navController.navigate(screen.route)
        }
      }
    }
  }
}

fun NavGraphBuilder.introGraph() {
  navigation(route = IntroBaseRoute.route, startDestination = Screen.Splash.route) {
    composable(route = Screen.Splash.route) {
      SplashScreen()
    }
  }
}

fun NavGraphBuilder.mainGraph(appWindowTitle: String) {
  navigation(route = Screen.MainBaseRoute.route, startDestination = Screen.Category.route) {
    composable(route = Screen.Category.route) {
      CategoryScreen(screenTitle = appWindowTitle)
    }
    composable(route = Screen.Source.route) { backStackEntry ->
      val categoryName = backStackEntry.arguments?.getString("categoryName")
      requireNotNull(categoryName) { stringResource(id = string.app_text_category_name_required) }

      SourceScreen(screenTitle = appWindowTitle, categoryName = categoryName)
    }
    composable(route = Screen.ArticleList.route) { backStackEntry ->
      val sourceId = backStackEntry.arguments?.getString("sourceId")
      requireNotNull(sourceId) { stringResource(id = string.app_text_source_id_required) }

      val sourceName = backStackEntry.arguments?.getString("sourceName")
      requireNotNull(sourceName) { stringResource(id = string.app_text_source_name_required) }

      ArticleListScreen(screenTitle = appWindowTitle, sourceId = sourceId, sourceName = sourceName)
    }
    composable(route = Screen.Article.route) { backStackEntry ->
      val url = Uri.decode(backStackEntry.arguments?.getString("url"))
      requireNotNull(url) { stringResource(id = string.app_text_url_required) }

      ArticleScreen(screenTitle = appWindowTitle, url = url)
    }
  }
}
