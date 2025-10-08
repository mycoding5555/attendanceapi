package kotlinapi.AttendanceApi.services

import org.springframework.stereotype.Service
import kotlinapi.AttendanceApi.dataSources.UserRepository
import kotlinapi.AttendanceApi.model.User


@Service
class userService(private val repository: UserRepository) {

    fun getusers(): List<User> {
        return repository.getUsers()
    }

}