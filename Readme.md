# Firebase Message App

This is a simple Android application that connects to **Firebase Realtime Database** and allows users to add messages with the text **"New message"**.

## Features
- Connects to **Firebase Realtime Database**.
- Adds a message **("New message")** when a button is clicked.
- Displays a list of messages retrieved from Firebase.

## Prerequisites
Before running the application, ensure you have:
1. **Android Studio** installed.
2. A **Firebase project** set up.
3. **Google Services JSON** file added to your project.

## Firebase Setup
1. **Create a Firebase Project**
    - Go to [Firebase Console](https://console.firebase.google.com/).
    - Click **"Add Project"** and follow the setup instructions.
    - Enable **Realtime Database**.

2. **Add Firebase to Android App**
    - In **Firebase Console**, go to **Project Settings > General**.
    - Click **"Add app"** (Android) and follow the instructions.
    - Download the **google-services.json** file and place it in your project's `app/` directory.

3. **Enable Realtime Database**
    - Go to **Firebase Console > Realtime Database**.
    - Click **"Create Database"**, select a region, and set rules.
    - Use the following rule for development:
      ```json
      {
        "rules": {
          ".read": true,
          ".write": true
        }
      }
      ```
    - Click **"Publish"**.

## Project Structure
```
/app
  |-- src/main/java/com/example/test_prototype_empty_app/
      |-- MainActivity.kt
      |-- DaoFactory.kt
  |-- res/
      |-- layout/
      |-- values/
  |-- AndroidManifest.xml
  |-- google-services.json
```

## Implementation

### **1. Initialize Firebase in `DaoFactory.kt`**
```kotlin
import android.util.Log
import com.google.firebase.database.*

data class Message(val text: String = "", val sender: String = "", val timestamp: Long = System.currentTimeMillis())

class DaoFactory {
    private val database = FirebaseDatabase.getInstance("https://your-database-url.firebaseio.com")
    private val messageRef = database.getReference("messages")

    fun addMessage() {
        val message = Message("New message")
        messageRef.push().setValue(message)
            .addOnSuccessListener { Log.d("Firebase", "Message added successfully.") }
            .addOnFailureListener { Log.e("Firebase", "Failed to add message.", it) }
    }
}
```

### **2. Call `addMessage()` in `MainActivity.kt`**
```kotlin
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private val daoFactory = DaoFactory()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageApp(daoFactory)
        }
    }
}

@Composable
fun MessageApp(daoFactory: DaoFactory) {
    var message by remember { mutableStateOf("No message yet") }
    
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = {
            daoFactory.addMessage()
            message = "New message added"
        }) {
            Text("Add Message")
        }
        Text(text = message, modifier = Modifier.padding(top = 16.dp))
    }
}
```

## Running the App
1. **Connect a device or use an emulator**.
2. **Run the project** in Android Studio.
3. Click the **"Add Message"** button to add a message to Firebase.

## Troubleshooting
- Ensure `google-services.json` is correctly placed in the `app/` directory.
- Check **Firebase Rules** if you get permission errors.
- Verify the **database URL** in `DaoFactory.kt`.


