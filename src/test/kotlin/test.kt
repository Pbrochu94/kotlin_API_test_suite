import org.junit.platform.suite.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*
import io.restassured.*
import kotlin.test.assertIsNot


val baseUrl = "https://jsonplaceholder.typicode.com"

class Case1 {
    @Test
    fun `simple get request returns 200`() {
        RestAssured.given()
            .baseUri(baseUrl)
            .get("/posts/1")
            .then()
            .statusCode(200)
    }
}

class Case2 {
    @Test
    fun `User ID and ID returns 1 in JSON`() {
        val expectedId = 1
        val expectedUserId = 1
        val response = RestAssured.given()
            .baseUri(baseUrl)
            .`when`()
            .get("/posts/1")
            .then()
            .extract()
        val actualId:Int = response.path("id")
        val actualUserId:Int = response.path("userId")
        assertEquals(actualId, expectedId, "Id does not match ${expectedId}.")
        assertEquals(actualUserId, expectedUserId, "User Id does not match ${expectedUserId}.")
    }
}

class Case3 {
    @Test
    fun `List every posts and assert 100 are present`() {
        val expectedNmbOfPosts = 100
        val posts:List<Map<String,Any>> = RestAssured.given()
            .baseUri(baseUrl)
            .`when`()
            .get("/posts")
            .then()
            .extract()
            .path("")
        posts.forEach{
            println(it)
        }
        val actualNmbOfPosts = posts.size
        assertEquals(expectedNmbOfPosts, actualNmbOfPosts)
    }
}

class Case4{
    @Test
    fun `assert title is not empty`(){
        val title: String = RestAssured.given()
            .baseUri(baseUrl)
            .`when`()
            .get("/posts/1")
            .then()
            .statusCode(200)
            .extract()
            .path("title")
        assertTrue(title.isNotBlank(), "Title is empty")
        println("Title: $title")
    }
}

class Case5{
    @Test
    fun `All users return has a UserId = 1`(){
        val body:List<Map<String,Any>> = RestAssured.given()
            .baseUri(baseUrl)
            .`when`()
            .queryParam("userId", "1")
            .get("/posts")
            .then()
            .statusCode(200)
            .extract()
            .path("")
        body.forEach{
            assertEquals(1,it["userId"], "`${it["title"]}` user ID is ${it["userId"]}")
        }
        println(body.size)
    }
}

class Case6{
    @Test
    fun `ID is 5 after dynamically changing the path`(){
        val response:Map<String,Any> = RestAssured.given()
            .baseUri("https://jsonplaceholder.typicode.com")
            .pathParam("id", "5")
            .`when`()
            .get("/posts/{id}")
            .then()
        .statusCode(200)
        .extract()
            .path("")
        assertEquals(5,response["id"], "ID of the returned post is not 5")
    }
}

class Case7{
    @Test
    fun`POST request is successfully posted and returns 201`(){
        val requestBody: String = """{
            "userId" : 11,
            "title" : "This is a brand new post",
            "body" : "This brand new post has been added by a POST api call"
            }"""
        val response:Map<String, Any> = RestAssured.given()
            .baseUri(baseUrl)
            .header("Content-Type", "application/json")
            .body(requestBody)
            .`when`()
            .post("/posts")
        .then()
        .statusCode(201)
        .extract()
            .path("")
        println(response::class)
        val actualTitle = response["title"]
        val expectedTitle = "This is a brand new post"
        assertAll(
            {assertEquals(expectedTitle,actualTitle)},
            {assertNotNull(response["id"])}
        )
    }
}

class Case8{

}