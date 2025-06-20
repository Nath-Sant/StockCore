import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Base64

fun main() {
    val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    println(Base64.getEncoder().encodeToString(key.encoded))
}