# Rest Api Demo

<p> Demo app for testing RestApi Calls using MVI pattern. The app includes a login, register and change password screens.
<p> The authentication is made through a token auto-generated by the server, when the user login or registers successfully.
<p> This token is then attached to the requests for making server calls and also used to check if the user is valid (logged) or invalid.
<p> Please note that less time was spent in the Design development. </p>
<br>

# Screenshots

![Intro Screen](screens/screen1.png "Intro screen")
![Login Screen](screens/screen2.png "Simple login screen")
![Blog List](screens/screen3.png "List of blog posts")
<br></br>
![Blog list filter](screens/screen4.png "List of Blog Posts with the filter option")
![Create a Blog Post](screens/screen5.png "Create and publish a blog post screen")
![Account Details](screens/screen6.png "Account details screen")
  <br>
  <br>
  
# Libraries Used
 * <p><b> Navigation Component  </b> - Handle app navigation and graph overview.</p>
 * <p><b>Lifecycles</b>- Create a UI that automatically responds to lifecycle events.</p>
 * <p><b>LiveData</b> - Build data objects that notify views when the underlying database changes.</p>
 * <p><b>Room</b> - Caching the network data for offline use.</p> 
 * <p><b>ViewModel</b> - Persist the data on configuration changes.</p>
 * <p><b>RecyclerView </b> - Display list and handle clicks.</p>
 * <p><b>Dagger 2</b> - For dependency injection.</p>
 * <p><b>Kotlin coroutines</b> - Asynchronous tasks.</p>
 * <p><b>Glide</b> - Image Loading.</p>
 * <p><b>Retrofit</b> - Http Requests.</p> 
 * <p><b>Gson</b> - Convert Json objects.</p>
 * <p><b>Leak Canary </b> - For leak analysis.</p>
 * <p><b>Material Dialogs</b> - Create custom dialogs.</p>
  <br>
  
# Non-Goals
  <p>The main focus of this project was on HTTP requests and caching the data for offline use using the MVI pattern.</p>
  <p>The UI was't developed for making the design look better, only added the necessary components to show the results.</p>
  <br>
  
# App architecture
  <p>The app was developed taking in account the best practices and recommended architecture from Google for building apps.</p>
  <p>It uses the repository pattern and the single source of truth. Each component depends only on the component one level below it.
  For example, activities depend only on a view model. The repository is the only class that depends on multiple other classes.</p>
  <div class="center" align="center">
    <img class="center" src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png" height="400">
  </div>
  <br>
  <br>
  <br>
  <br>
  <br>

### Disclaimer
###### The code was written as part of a course on android architecture and other components from <a href="http://https://codingwithmitch.com"> codingwithmitch.com</a></p>
