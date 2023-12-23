pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "DemoNewsCompose"
include(":app")
include(":domain")
include(":data")
include(":framework")
include(":features:feature_splash")
include(":features:feature_category")
include(":features:feature_source")
include(":features:feature_article_list")
include(":features:feature_article")
include(":features:feature_error_message")
