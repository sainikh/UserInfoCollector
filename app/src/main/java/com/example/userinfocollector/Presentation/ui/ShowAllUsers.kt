package com.example.userinfocollector.Presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.userinfocollector.Presentation.ViewModel.UserViewModel
import com.example.userinfocollector.Presentation.data.UserToDisplay
import com.example.userinfocollector.Presentation.theme.UserInfoCollectorTheme

@Composable
fun UserListItem(user: UserToDisplay) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            UserRow(user)
        }
    }
}


@Composable
fun ShowUserList(users: List<UserToDisplay>) {
    Column (
        modifier = Modifier.padding(10.dp,50.dp)
    ){
        Header(title = "User details")
        LazyColumn {
            items(users) { user ->
                UserListItem(user = user)
            }
        }

    }
}

@Composable
fun UserRow(user: UserToDisplay) {

    Row {
        BasicText(text = "User Name : ", style = MaterialTheme.typography.titleMedium)
        BasicText(text = user.name, style = MaterialTheme.typography.bodyMedium)
    }
    Row {

        BasicText(text = "Date of Birth : ",  style = MaterialTheme.typography.titleMedium)
        BasicText(text = user.dob, style = MaterialTheme.typography.bodyMedium)
    }
    Row {

        BasicText(text = "Age : ", style = MaterialTheme.typography.titleMedium)
        BasicText(text = user.age.toString(), style = MaterialTheme.typography.bodyMedium)
    }
    Row {

        BasicText(text = "Address : ",  style = MaterialTheme.typography.titleMedium)
        BasicText(text = user.address, style = MaterialTheme.typography.bodyMedium)
    }
}




@Composable
fun ShowAllUsersScreen(viewModel: UserViewModel) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if(viewModel.allUsers.value != null){
            ShowUserList(viewModel.allUsers.value!!)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowAllUsersPreview() {
    UserInfoCollectorTheme {
        val viewModel = UserViewModel(applicationContext = LocalContext.current)
//        ShowAllUsersScreen(viewModel)
        val users = listOf(
            UserToDisplay("sai", 30, "30-01-01","Hyderabad"),
            UserToDisplay("nikhil",  30, "30-01-01","Bengaluru")
        )
        ShowUserList(users)
    }
}

