package com.example.userinfocollector.Presentation.ViewModel

import com.example.userinfocollector.data.User
import com.example.userinfocollector.data.UserRepository
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userinfocollector.Presentation.data.UserToDisplay
import com.example.userinfocollector.domain.CheckUserExistsUseCase
import com.example.userinfocollector.data.UserDatabase
import com.example.userinfocollector.domain.UserUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UserViewModel(applicationContext: Context) : ViewModel() {

    private val repository: UserRepository
    private var _allUsers: MutableLiveData<List<UserToDisplay>> = MutableLiveData<List<UserToDisplay>>()
    val allUsers: LiveData<List<UserToDisplay>> get() = _allUsers

    private val toastEventChannel = Channel<String>()
    val toastEvents = toastEventChannel.receiveAsFlow()

    init {
        val userDao = UserDatabase.getDatabase(applicationContext).userDao()
        repository = UserRepository(userDao)
    }

    fun insert(name: String, age: Int, dob: Long, address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            //check if user already exists
            val usernameExists = CheckUserExistsUseCase(repository).invoke(name)
            if (!usernameExists) {
                repository.insert(User(name = name, age = age, dobTimeStamp = dob, address = address))
                //show error message
                toastEventChannel.send("Successfully Updated $name details")
            } else {
                //show error message
                toastEventChannel.send("User already exists")
            }
        }
    }

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
           val list  = repository.getAllUsers().map {
                UserToDisplay(
                    name = it.name,
                    age = it.age,
                    dob = UserUtils.getFormattedDateFormLong(
                        it.dobTimeStamp
                    ),
                    address = it.address
                )
            }
            _allUsers.postValue(list)
        }
    }
}
