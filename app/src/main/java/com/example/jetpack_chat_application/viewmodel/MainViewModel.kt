package com.example.jetpack_chat_application.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_chat_application.model.MainUserModel
import com.example.jetpack_chat_application.repository.UserRepository
import com.example.jetpack_chat_application.utils.TAG
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseDatabase: FirebaseDatabase
    ) : ViewModel() {

    private val _user = MutableLiveData<MainUserModel>()
    val user = _user as LiveData<MainUserModel>
    val _username = MutableLiveData<String>()
    val _token = MutableLiveData<String>()

    private fun getLoggedUser(){
        viewModelScope.launch {
            userRepository.userFlow.collect{
                _user.value = it
                _username.value = it.email?.split("@")?.get(0)
                if (!_token.value.isNullOrEmpty()) {
                    if (!_username.value.isNullOrEmpty()){
                        firebaseDatabase.getReference("users/${_username.value}").child("token").setValue(_token.value).addOnSuccessListener {
                        }.addOnFailureListener {it2->
                            Log.i(TAG,it2.toString())
                        }
                    }
                }
            }
        }
    }


    fun saveLoggedUser(email : String , password : String){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.setUser(email,password)
        }
    }

    init {
        getLoggedUser()
        FirebaseMessaging.getInstance().token.addOnCompleteListener { it ->
            _token.value = it.result
        }.addOnFailureListener {
            Log.i(TAG,it.toString())
        }
    }
}