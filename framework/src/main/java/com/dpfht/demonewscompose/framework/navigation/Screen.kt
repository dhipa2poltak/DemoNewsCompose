package com.dpfht.demonewscompose.framework.navigation

import android.net.Uri

sealed class Screen(val route: String) {
  object IntroBaseRoute: Screen("intro")
  object MainBaseRoute: Screen("main")

  object None: Screen("")
  object NavigateUp: Screen("navigateUp")

  object Splash: Screen("splash")
  object Category: Screen("category")
  data class Source(val categoryName: String) : Screen("") {
    companion object {
      const val route = "source/{categoryName}"
    }

    fun createRoute() = "source/$categoryName"
  }
  data class ArticleList(val sourceId: String, val sourceName: String, val categoryName: String) : Screen("") {
    companion object {
      const val route = "article_list/{sourceId}/{sourceName}/{categoryName}"
    }

    fun createRoute() = "article_list/$sourceId/$sourceName/$categoryName"
  }
  data class Article(val title: String, val url: String) : Screen("") {
    companion object {
      const val route = "article/{title}/{url}"
    }

    fun createRoute() = "article/$title/${Uri.encode(url)}"
  }

  data class Error(val message: String): Screen("")
}
