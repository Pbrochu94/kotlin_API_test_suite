import org.junit.platform.suite.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*
import io.restassured.RestAssured
val baseUrl = "https://jsonplaceholder.typicode.com"

class Case1 {
    @Test
    fun `API returns 200`() {
        RestAssured.given()
            .baseUri(baseUrl)
            .get("/posts/1")
            .then()
            .statusCode(200)
    }
}