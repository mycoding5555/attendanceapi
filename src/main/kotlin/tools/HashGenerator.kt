package tools

import org.mindrot.jbcrypt.BCrypt

/**
 * Small helper to generate a BCrypt hash for a single argument password.
 * Usage (via Gradle run):
 * ./gradlew run --args='myPassword123'
 */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: provide a password as first argument")
        return
    }
    val pwd = args[0]
    val hashed = BCrypt.hashpw(pwd, BCrypt.gensalt())
    println(hashed)
}
