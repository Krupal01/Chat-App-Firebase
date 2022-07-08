package com.example.jetpack_chat_application.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.*
import com.example.jetpack_chat_application.model.MainUserModel
import com.example.jetpack_chat_application.model.MessageModel
import com.example.jetpack_chat_application.repository.UserRepository
import com.example.jetpack_chat_application.utils.TAG
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage,
    private val userRepository: UserRepository
    ) : ViewModel() {

    private val _chatContact = mutableStateListOf<String>()
    val chatContact : List<String> = _chatContact

    private val _key = MutableLiveData<String>()
    val key : LiveData<String> = _key

    private val _user = MutableLiveData<String>()
    val userName : LiveData<String> = _user

    private val _chatMessage = mutableStateListOf<MessageModel>()
    val chatMessage : List<MessageModel> = _chatMessage

    var _photoAttachmentUri = MutableLiveData<Uri?>()
    val photoAttachmentUri  = _photoAttachmentUri as  LiveData<Uri?>

    var imageUrl : String? = null

    init {

        viewModelScope.launch {
            userRepository.userFlow.collect{
                _user.value = it.email?.split("@")?.get(0)

                userName.value?.let { it1 -> getChatContact(it1) }

            }
        }

    }


    fun getKey(user2 : String) {
        val databaseReference = firebaseDatabase.getReference("message")
        databaseReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                _key.value = ""
                for (data in snapshot.children){
                    if (data.key?.contains(user2) == true && data.key?.contains(userName.value!!) == true){
                        _key.value = data.key!!
                    }
                }
                if (_key.value.isNullOrEmpty()){
                    _key.value = "$user2 with ${userName.value}"
                }
                databaseReference.removeEventListener(this)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(TAG,error.message)
            }
        })
    }


    private fun getChatContact(username : String){
        val databaseReference = firebaseDatabase.getReference("users")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _chatContact.clear()
                for (data in snapshot.children){
                    if (!data.key.isNullOrEmpty()){
                        if (data.key != username){
                            _chatContact.add(data.key!!)
                        }
                    }else{
                        Log.i(TAG,"No data found ${data.key}")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(TAG,error.message)

            }
        })
    }


    fun getMessage(key : String){
        val databaseReference = firebaseDatabase.getReference("message").child(key)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _chatMessage.clear()
                for (data in snapshot.children){
                    if (data.hasChild("image")){
                        _chatMessage.add(MessageModel(msg = null , imageUrl = data.child("image").value.toString()))
                    }else{
                        if (data.value.toString().isNotEmpty()){
                            _chatMessage.add(MessageModel(msg = data.value!!.toString() , imageUrl = null))
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(TAG,error.message)

            }
        })
    }

    fun sendMessage(key: String , msg : String){
        firebaseDatabase.getReference("message").child(key).push().setValue(msg)
    }

    fun uploadImage(key: String){
        if (_photoAttachmentUri!=null){
            val filename = UUID.randomUUID().toString()
            val ref = firebaseStorage.getReference("$key/images/$filename")
            ref.putFile(_photoAttachmentUri.value!!)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        imageUrl = it.toString()
                        firebaseDatabase.getReference("message")
                            .child(key)
                            .push()
                            .child("image")
                            .setValue(imageUrl)
                            .addOnSuccessListener {
                                imageUrl = null
                                _photoAttachmentUri.value = null
                                Log.i("$TAG viewmodelphoto",photoAttachmentUri.value.toString())
                            }

                    }
                }
        }else{
            return
        }
    }

}