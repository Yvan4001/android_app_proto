package com.example.test_prototype_empty_app

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

data class Message(
    val text: String = "",
    val sender: String = "",
    val timestamp: Long = Timestamp.now().seconds
)
class DaoFactory {
    private val database = FirebaseDatabase.getInstance("https://android-prototype-empty-app-default-rtdb.firebaseio.com/")
    private val messageRef = database.getReference("messages")
    fun setupDatabase() {
        messageRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = mutableListOf<Message>()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    message?.let { messages.add(it) }
                }
                Log.d("Firebase", "Messages: $messages")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read value.", error.toException())
            }
        })
    }

    fun addMessage(text: String) {
        val message = Message(text, System.currentTimeMillis().toString())
        val newMessageRef = messageRef.push() // Generate a unique key
        newMessageRef.setValue(message)
            .addOnSuccessListener {
                Log.d("Firebase", "Message added successfully.")
            }
            .addOnFailureListener { error ->
                Log.e("Firebase", "Failed to add message.", error)
            }
    }

    fun updateMessage(messageId: String, text: String) {
        val messageRef = FirebaseDatabase.getInstance().getReference("messages").child(messageId)
        messageRef.child("text").setValue(text)
            .addOnSuccessListener { Log.d("Firebase", "Message updated successfully.")}
            .addOnFailureListener { error -> Log.e("Firebase", "Failed to update message.", error) }
    }
}