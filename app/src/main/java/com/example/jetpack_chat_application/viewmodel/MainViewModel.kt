package com.example.jetpack_chat_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_chat_application.model.MainUserModel
import com.example.jetpack_chat_application.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor( private val userRepository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<MainUserModel>()
    val user = _user as LiveData<MainUserModel>

    private fun getLoggedUser(){
        viewModelScope.launch {
            userRepository.userFlow.collect{
                _user.value = it
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
    }
}