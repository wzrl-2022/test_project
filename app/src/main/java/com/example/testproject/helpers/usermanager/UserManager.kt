package com.example.testproject.helpers.usermanager

import com.example.testproject.db.TestDatabase
import com.example.testproject.db.asUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserManager @Inject constructor(
    private val database: TestDatabase
) : IUserManager {
    private var _user: User? = null

    override fun getUser(): User? {
        return _user?.copy()
    }

    override suspend fun readUser() =
        withContext(Dispatchers.IO) {
            _user = database.userDao().readUser()?.asUser()
        }

    override suspend fun saveUser(user: User) =
        withContext(Dispatchers.IO) {
            database.userDao().insert(user.asUserEntity())
            _user = user
        }

    override suspend fun updateUser(user: User) =
        withContext(Dispatchers.IO) {
            database.userDao().update(user.asUserEntity())
            _user = user
        }

    override suspend fun deleteUser(user: User) =
        withContext(Dispatchers.IO) {
            database.userDao().delete(user.asUserEntity())
            _user = null
        }
}