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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleScreen(url: String) {
  val isLoaded = remember { mutableStateOf(false) }
  val isRefreshing = remember { mutableStateOf(false) }

  val pullRefreshState = rememberPullRefreshState(
    refreshing = isRefreshing.value || !isLoaded.value,
    onRefresh = {
      isRefreshing.value = true
    }
  )

  Scaffold(
    content = { padding ->
      Box(
        modifier = Modifier
          .padding(padding)
          .fillMaxSize()
          .pullRefresh(pullRefreshState)
          .verticalScroll(rememberScrollState())
      ) {
          WebViewPage(url = url, isLoaded, isRefreshing)

          PullRefreshIndicator(
            refreshing = isRefreshing.value || !isLoaded.value,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
          )
      }
    }
  )
}

@Composable
fun WebViewPage(url: String, isLoaded: MutableState<Boolean>, isRefreshing: MutableState<Boolean>) {
  AndroidView(
    factory = { context ->
      WebView(context).apply {
        layoutParams = LayoutParams(
          LayoutParams.MATCH_PARENT,
          LayoutParams.MATCH_PARENT
        )
        webViewClient = object : WebViewClient() {
          @Deprecated("Deprecated in Java", ReplaceWith("false"))
          override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return false
          }

          override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
          }

          override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            isLoaded.value = true
            isRefreshing.value = false
          }
        }
      }
    },
    update = {
      if (!isLoaded.value) {
        it.loadUrl(url)
      }

      if (isRefreshing.value) {
        it.reload()
      }
    }
  )
}
