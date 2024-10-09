package com.example.testproject.helpers.usermanager

interface IUserManager {
    fun getUser(): User?
    suspend fun readUser()
    suspend fun saveUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
}