package com.example.userinfocollector.Presentation.ViewModel

import com.example.userinfocollector.data.model.User
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userinfocollector.Presentation.Mapper.UserEntityMapper
import com.example.userinfocollector.Presentation.data.UserToDisplay
import com.example.userinfocollector.domain.usecase.CheckUserExistsUseCase
import com.example.userinfocollector.data.local.dataBase.UserDatabase
import com.example.userinfocollector.data.local.repository.UserRepositoryImpl
import com.example.userinfocollector.domain.usecase.GetAllUsersUseCase
import com.example.userinfocollector.domain.usecase.SaveUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UserViewModel(applicationContext: Context) : ViewModel() {

    private var _allUsers: MutableLiveData<List<UserToDisplay>> =
        MutableLiveData<List<UserToDisplay>>()
    val allUsers: LiveData<List<UserToDisplay>> get() = _allUsers

    private val toastEventChannel = Channel<String>()
    val toastEvents = toastEventChannel.receiveAsFlow()

    private val userDao = UserDatabase.getDatabase(applicationContext).userDao()
    private val repository: UserRepositoryImpl = UserRepositoryImpl(userDao)


    fun insert(name: String, age: Int, dob: Long, address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            //check if user already exists
            val usernameExists = CheckUserExistsUseCase(repository).invoke(name)
            if (!usernameExists) {
                SaveUserUseCase(repository).execute(
                    User(
                        name = name,
                        age = age,
                        dobTimeStamp = dob,
                        address = address
                    )
                )
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
            val userList = GetAllUsersUseCase(repository, UserEntityMapper()).invoke()
            _allUsers.postValue(userList)
        }
    }
}
