# DemoNewsCompose

**Screenshots:**
| <img src="docs/screenshots/category.jpg">  | <img src="docs/screenshots/source.jpg">     |
| -------------------------------------------| ------------------------------------------- |
| <img src="docs/screenshots/articles.jpg">  | <img src="docs/screenshots/article.jpg">    |

**Technology Stack:**
- Kotlin Programming Language
- Clean Architecture
- MVVM Architecture Pattern
- Hilt Dependency Injection
- Jetpack Compose
- ViewModel
- Coroutine
- Flow
- Retrofit REST + OkHttp
- GSON Serialization
- Coil
- Gradle build flavors
- BuildSrc + Kotlin DSL
- Proguard
- Git

**To run the project in DEBUG MODE:**

Get the api key from [newsapi.org](https://newsapi.org/).

Create file namely “key.properties” in the root project with following contents:

storePassword=<your_keystore_password> <br />
keyPassword=<your_key_password> <br />
keyAlias=<your_key_alias> <br />
storeFile=<path_to_keystore_file> <br />
apiKey=<your_api_key> <br />

once you have created it, open the project with Android Studio, build the project and run the project.
