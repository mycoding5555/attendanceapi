package kotlinapi.AttendanceApi.dataSources

import kotlinapi.AttendanceApi.model.User
import org.springframework.stereotype.Repository

@Repository
class UserRepository{

    val users = mutableListOf<User>()
    

    fun getusers(): List<User> {
        return users
    }
}