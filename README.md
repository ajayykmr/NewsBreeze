# NewsBreeze


This app follows the MVVM architecture design pattern where the Model-View-ViewModel are separated from each other.

This app gets the latest breaking news articles and shows them in a RecyerView.


The UI has been designed as given in the assignment.

Glide library is used to display images from an image URL into an ImageView.

Navigation Components and Fragments are used to navigate between different UI screens.

Bundles are used to pass data between different Fragments.


It gets the news from an API: https://newsapi.org

Retrofit library is used to interact with the given API and make the required GET requests.

Since the API does not return author's profile picture and his position, I have used a dummy profile picture and "Unknown Position" to match the given UI.

Also, since I'm using the developer plan, the API won't return the complete content from news articles.


The Save button saves the article in a local SQLiteDatabase.

I've used the ROOM library to interact with the SQLiteDatabase.

The saved articles can be accessed from bookmark icon on the top right corner.

In case of no internet connection the saved articles can still be accessed.


(MyAdapter is the adapter used for RecyclerView present in the Home Screen, 

BookmarkAdapter is the adapter used for RecyclerView present in the Bookmark Screen
