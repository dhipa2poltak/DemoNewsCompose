package com.dpfht.demonewscompose.feature_article

import android.graphics.Bitmap
import android.view.ViewGroup.LayoutParams
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.dpfht.demonewscompose.feature_article.event_state.UIEvent
import com.dpfht.demonewscompose.framework.commons.ui.components.SharedTopAppBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleScreen(
  screenTitle: String,
  url: String,
  viewModel: ArticleViewModel = hiltViewModel()
) {
  val isLoading = remember { mutableStateOf(true) }

  /*
  val pullRefreshState = rememberPullRefreshState(
    refreshing = isLoading.value,
    onRefresh = {
      //isLoading.value = true
    }
  )
  */

  Scaffold(
    topBar = {
      SharedTopAppBar(
        title = screenTitle,
        onNavigateBack = {
          viewModel.onEvent(UIEvent.OnBackPressed)
        }
      )
    },
    content = { padding ->
      Box(
        modifier = Modifier
          .padding(padding)
          .fillMaxSize()
      ) {
        ConstraintLayout(
          modifier = Modifier
            .fillMaxSize()
        ) {

          val (articleBox) = createRefs()

          Box(
            modifier = Modifier
              .fillMaxSize()
              .constrainAs(articleBox) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
              }
              //.pullRefresh(pullRefreshState)
              //.verticalScroll(rememberScrollState())
          ) {
            WebViewPage(url = url, isLoading)

            /*
            PullRefreshIndicator(
              refreshing = isLoading.value,
              state = pullRefreshState,
              modifier = Modifier.align(Alignment.TopCenter)
            )
            */
          }
        }
      }
    }
  )
}

@Composable
fun WebViewPage(url: String, isLoading: MutableState<Boolean>) {
  AndroidView(
    factory = { context ->
      WebView(context).apply {
        layoutParams = LayoutParams(
          LayoutParams.MATCH_PARENT,
          LayoutParams.MATCH_PARENT
        )
        webViewClient = object : WebViewClient() {
          override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return false
          }

          override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)

            //isLoading.value = true
          }

          override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            isLoading.value = false
          }
        }
        loadUrl(url)
      }
    },
    update = {
      //if (isLoading.value) {
        it.loadUrl(url)
      //}
    }
  )
}
