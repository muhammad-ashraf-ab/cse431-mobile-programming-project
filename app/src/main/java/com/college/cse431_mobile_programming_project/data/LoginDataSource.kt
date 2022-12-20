package com.college.cse431_mobile_programming_project.data

import com.college.cse431_mobile_programming_project.data.model.LoggedInUser
import java.io.IOException
import java.util.*
import kotlin.random.Random

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    private val genders = listOf("men", "women")

    fun login(username: String = "John Doe", password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(
                UUID.randomUUID().toString(), username,
            "https://randomuser.me/api/portraits/${genders[Random.nextInt(genders.size)]}/${Random.nextInt(50)}.jpg")
//            val user = LoggedInUser(uid, email, )
//            return Result.Success(user)
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}