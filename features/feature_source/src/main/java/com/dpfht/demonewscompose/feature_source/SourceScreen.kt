package com.dpfht.demonewscompose.feature_source

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.dpfht.demonewscompose.feature_source.event_state.UIEvent
import com.dpfht.demonewscompose.framework.commons.ui.components.RowItem
import com.dpfht.demonewscompose.framework.commons.ui.components.SharedTopAppBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SourceScreen(
  modifier: Modifier = Modifier,
  screenTitle: String,
  categoryName: String,
  viewModel: SourceViewModel = hiltViewModel()
) {
  val state = viewModel.uiState.value

  val pullRefreshState = rememberPullRefreshState(
    refreshing = state.isLoading,
    onRefresh = {
      viewModel.onEvent(UIEvent.Refresh)
    }
  )

  LaunchedEffect(Unit) {
    viewModel.onEvent(UIEvent.Init(categoryName))
  }

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
            .padding(top = 16.dp)
            .fillMaxSize()
        ) {

          val (tvTitle, divider, sourceBox) = createRefs()

          val colors = MaterialTheme.colorScheme

          Text(
            String.format(stringResource(id = R.string.source_text_source), categoryName),
            color = Color.Black,
            modifier = modifier
              .padding(vertical = 8.dp)
              .constrainAs(tvTitle) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(divider.top)
              },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
          )

          HorizontalDivider(
            modifier = modifier
              .height(1.dp)
              .constrainAs(divider) {
                start.linkTo(parent.start)
                top.linkTo(tvTitle.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(sourceBox.top)
              },
            color = colors.onSurface.copy(alpha = .2f)
          )

          Box(
            modifier = Modifier
              .fillMaxSize()
              .constrainAs(sourceBox) {
                start.linkTo(parent.start)
                top.linkTo(divider.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
              }
              .pullRefresh(pullRefreshState)
          ) {
            if (state.sources.isNotEmpty()) {
              LazyColumn(
                modifier = modifier
                  .fillMaxSize()
              ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                items(state.sources.size) { index ->
                  RowItem(
                    label = state.sources[index].name,
                    item = state.sources[index],
                    onClickItem = { item ->
                      viewModel.onEvent(UIEvent.OnClickSourceItem(item.id, item.name))
                    }
                  )
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }
              }
            }

            PullRefreshIndicator(
              refreshing = state.isLoading,
              state = pullRefreshState,
              modifier = Modifier.align(Alignment.TopCenter)
            )
          }
        }
      }
    }
  )
}
