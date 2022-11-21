package com.haeseong.composetest.ui.home

import androidx.compose.runtime.Composable


data class User(val name: String, val age: Int)

fun userResponse(): List<User> {
    val list = mutableListOf<User>()
    for (i in 1..30) {
        list.add(
            User("유저$i", i)
        )
    }
    return list
}

@Composable
fun UserCard(user: User) {

}