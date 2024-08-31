# User Management App

## Description
The User Management App is an Android application designed to fetch, display, and manage user data. It retrieves user information from the ReqRes API, stores it locally using the Room persistence library, and allows CRUD (Create, Read, Update, Delete) operations on the user data. The app follows the MVVM architecture pattern and is developed entirely in Java.

## Installation
To set up and run this project locally, follow these steps:

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/boaz1e/usermanagementapp
   ```

2. **Open in Android Studio:**
   - Open Android Studio and select `Open an existing project`.
   - Navigate to the cloned repository and select the project directory.

3. **Sync Project with Gradle:**
   - Android Studio should automatically sync the project with Gradle. If not, click on `File > Sync Project with Gradle Files`.

## Building and Running the App
To build and run the application:

1. **Build the APK:**
   - Go to `Build > Build Bundle(s)/APK(s) > Build APK(s)` in Android Studio.
   - After the build is complete, locate the APK file in the `app/build/outputs/apk/debug/` directory.

2. **Run the App:**
   - Connect an Android device or start an emulator.
   - Click on the `Run` button in Android Studio, or use `Shift + F10`.

## Usage
1. **Fetching Users:**
   - On launching the app, it fetches user data from the ReqRes API and stores it locally.
   
2. **Viewing Users:**
   - Users are displayed in a list format using a RecyclerView. Each user card shows the avatar, name, and email.
   
3. **CRUD Operations:**
   - **Add User:** Click on the `+` button to add a new user. Fill in the user details in the dialog that appears.
   - **Update User:** Click on the `Update` button on a user card to modify user details.
   - **Delete User:** Click on the `Delete` button on a user card to remove the user from the list.

4. **Persisted Data:**
   - The app uses a local Room database to persist user data. Even after closing and reopening the app, the users will remain stored locally.


## Assumptions
- The API returns valid and well-formed JSON responses.
- Users added through the app are not persisted on the API, but only in the local Room database.

## Challenges
- **Handling API Errors:** Managing network errors and ensuring the app remains functional even when the API fails.
  - **Solution:** Implemented error handling mechanisms and displayed appropriate messages to the user when API calls fail.

- **Data Consistency:** Ensuring that the local database and UI remain in sync, especially after CRUD operations.
  - **Solution:** Used LiveData to observe database changes and automatically update the UI.

## Features
- Fetch users from the ReqRes API.
- Display users in a RecyclerView with images.
- Perform CRUD operations: Add, Update, and Delete users.
- Persist data locally using Room.
- Enhanced UI with CardView, Dialogs, and Image loading via Glide.

## Technologies Used
- **Programming Language:** Java
- **Architecture:** MVVM (Model-View-ViewModel)
- **Networking:** Retrofit
- **Database:** Room Persistence Library
- **UI Components:** RecyclerView, CardView, Dialogs, Glide for image loading
- **Version Control:** Git, GitHub


