# News App

This application is an Android app for displaying news articles and adding them to favorites. The app fetches news data from a REST API and stores it locally in a database.

## Technologies

- **Kotlin**: The primary programming language for Android development.
- **Jetpack Compose**: A modern and declarative UI toolkit used for building user interfaces.
- **Room Database**: A database library for Android that stores application data locally.
- **Retrofit**: An HTTP client used for interacting with REST APIs.
- **Coil**: A library for loading and displaying images.
- **Accompanist SwipeRefresh**: Supports data refresh actions.

## Features

- **Home Screen**: Fetches and displays news articles from the API. Users can click on articles to view details.
- **Favorites**: Displays articles that users have added to their favorites.
- **Search**: Allows filtering of articles by author name.

## Usage

### API Integration

The app uses [NewsAPI](https://newsapi.org/) to fetch news data. Retrofit is used to make API requests.

### Database

Room Database is used to store news articles locally. The app keeps track of articles that users have added to their favorites.

### Jetpack Compose

The user interface of the app is built using Jetpack Compose, providing a modern and reactive UI.

### Swipe Refresh

The app uses the `accompanist-swiperefresh` library to support data refreshing. This allows users to refresh the news list.

## Setup

1. Clone the project.
2. Open the project in Android Studio.
3. Sync Gradle to download required dependencies.
4. Update your API key in the `apiService`.

```kotlin
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://newsapi.org/v2/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService: MyApiService = retrofit.create(MyApiService::class.java)
```

5. Run the app and start viewing news articles.
