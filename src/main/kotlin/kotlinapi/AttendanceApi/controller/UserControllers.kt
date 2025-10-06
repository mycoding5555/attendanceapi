package kotlinapi.AttendanceApi.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlinapi.AttendanceApi.model.User
import kotlinapi.AttendanceApi.services.userService
import org.springframework.web.bind.annotation.GetMapping


@RestController
@RequestMapping("/users")
class UserControllers {

    private val service = userService()
    @GetMapping

    fun getUsers(): List<User> {
        return service.getusers()
    }
}