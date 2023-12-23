package com.dpfht.demonewscompose.feature_category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.dpfht.demonewscompose.domain.entity.CategoryEntity
import com.dpfht.demonewscompose.feature_category.event_state.UIEvent
import com.dpfht.demonewscompose.framework.commons.ui.components.SharedTopAppBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryScreen(
  modifier: Modifier = Modifier,
  screenTitle: String,
  viewModel: CategoryViewModel = hiltViewModel()
) {
  val state = viewModel.uiState.value

  val pullRefreshState = rememberPullRefreshState(
    refreshing = state.isLoading,
    onRefresh = {
      viewModel.onEvent(UIEvent.Refresh)
    }
  )

  LaunchedEffect(Unit) {
    viewModel.onEvent(UIEvent.Init)
  }

  Scaffold(
    topBar = {
      SharedTopAppBar(title = screenTitle)
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

          val (tvTitle, divider, categoryBox) = createRefs()

          val colors = MaterialTheme.colorScheme

          Text(
            stringResource(id = R.string.category_text_category),
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
                bottom.linkTo(categoryBox.top)
              },
            color = colors.onSurface.copy(alpha = .2f)
          )

          Box(
            modifier = Modifier
              .fillMaxSize()
              .constrainAs(categoryBox) {
                start.linkTo(parent.start)
                top.linkTo(divider.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
              }
              .pullRefresh(pullRefreshState)
          ) {
            if (state.categories.isNotEmpty()) {
              LazyColumn(
                modifier = modifier
                  .fillMaxSize()
              ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                items(state.categories.size) { index ->
                  CategoryItem(
                    category = state.categories[index],
                    onClickCategoryItem = { categoryName ->
                      viewModel.onEvent(UIEvent.OnClickCategoryItem(categoryName))
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

@Composable
fun CategoryItem(
  category: CategoryEntity,
  onClickCategoryItem: (categoryName: String) -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .clip(RoundedCornerShape(8.dp))
      .clickable { onClickCategoryItem(category.categoryName) },
    shape = RoundedCornerShape(8.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(
      defaultElevation = 8.dp
    )
  ) {
    Text(
      modifier = Modifier.padding(8.dp),
      text = category.categoryName
    )
  }
}
