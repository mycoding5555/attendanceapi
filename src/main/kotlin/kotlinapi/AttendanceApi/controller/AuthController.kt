package kotlinapi.AttendanceApi.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlinapi.AttendanceApi.dataSources.UserRepository
import kotlinapi.AttendanceApi.model.User
import org.mindrot.jbcrypt.BCrypt

data class LoginRequest(val email: String, val password: String)
data class UserResponse(val id: Int, val name: String, val email: String)

@RestController
@RequestMapping("/auth")
class AuthController(private val repository: UserRepository) {

    // use jBCrypt directly

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<Any> {
        val user: User? = repository.getUserByEmail(req.email)
    return if (user != null && BCrypt.checkpw(req.password, user.password)) {
            val resp = UserResponse(id = user.id ?: -1, name = user.name, email = user.email)
            ResponseEntity.ok(resp)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Invalid credentials"))
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody req: LoginRequest): ResponseEntity<Any> {
        // basic validation
        if (req.email.isBlank() || req.password.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to "email and password required"))
        }

        // check existing
        val existing = repository.getUserByEmail(req.email)
        if (existing != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to "email already registered"))
        }

    // hash password
    val hashed = BCrypt.hashpw(req.password, BCrypt.gensalt())
    val toCreate = User(id = null, name = req.email.substringBefore('@'), email = req.email, password = hashed)
        val created = repository.createUser(toCreate)
        val resp = UserResponse(id = created.id ?: -1, name = created.name, email = created.email)
        return ResponseEntity.status(HttpStatus.CREATED).body(resp)
    }

}
