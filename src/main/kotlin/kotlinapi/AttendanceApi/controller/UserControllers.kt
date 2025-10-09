package kotlinapi.AttendanceApi.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlinapi.AttendanceApi.model.User
import kotlinapi.AttendanceApi.services.userService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.http.HttpStatus
import org.mindrot.jbcrypt.BCrypt


@RestController
@RequestMapping("/users")
class UserControllers(private val service: kotlinapi.AttendanceApi.services.userService) {

    @GetMapping
    fun getUsers(): List<User> = service.getUsers()

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): ResponseEntity<User> {
        val user = service.getUser(id)
        return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        // Ignore id from client - DB will generate
    val hashed = BCrypt.hashpw(user.password, BCrypt.gensalt())
    val toCreate = user.copy(id = null, password = hashed)
        val created = service.createUser(toCreate)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Int, @RequestBody user: User): ResponseEntity<Void> {
        val updated = service.updateUser(id, user)
        return if (updated) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Int): ResponseEntity<Void> {
        val deleted = service.deleteUser(id)
        return if (deleted) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
    }

}