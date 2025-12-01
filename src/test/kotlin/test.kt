import org.junit.platform.suite.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*
import io.restassured.*
import org.example.Post
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
@Test
fun `PUT request fully modify a post`(){
    val modifiedPost = """{
        "userId": 1001,
        "id": 1,
        "title": "Harry Potter",
        "body": "This is a brand new post"
    }"""
    val response:Map<String, Any> = RestAssured.given()
        .baseUri(baseUrl)
    .header("Content-Type", "application/json")
    .body(modifiedPost)
        .`when`()
        .put("/posts/1")
        .then()
        .statusCode(200)
        .extract()
        .path("")
        assertAll(
            {assertEquals(1001, response["userId"])},
            {assertEquals(1, response["id"])},
            {assertEquals("Harry Potter", response["title"])},
            {assertEquals("This is a brand new post", response["body"])}
        )
}
}

class Case9{
    @Test
    fun `partially modify a post`(){
//        val before: Map<String,Any> = RestAssured.given()
//            .baseUri(baseUrl)
//            .`when`()
//            .get("/posts/1")
//            .then()
//            .statusCode(200)
//            .extract()
//            .path("")
//        println(before)
        val modification:Map<String, Any> = mapOf(
            "title" to "Star wars"
        )
        val response:Map<String, Any> = RestAssured.given()
            .baseUri(baseUrl)
            .header("content-type", "application/json")
            .body(modification)
            .`when`()
            .patch("/posts/1")
            .then()
            .statusCode(200)
            .extract()
        .path("")
        //println(response)
    }
}

class Case10{
    @Test
    fun `DELETE call successfully returns 200`(){
        val response:MutableMap<String, Any> =RestAssured.given()
            .baseUri(baseUrl)
            .`when`()
            .delete("/posts/1")
            .then()
            .statusCode(200)
            .extract()
        .path("")
        //println(response)
    }
}

class Case11{
    @Test
    fun `Call response conversion into data class object and verification of properties`(){
        val response:Post = RestAssured.given()
            .baseUri(baseUrl)
            .`when`()
            .get("/posts/2")
            .then()
            .extract()
            .`as`(Post::class.java)
        assertAll(
            {assertEquals(1, response.userId)},
            {assertEquals(2, response.id)},
            {assertEquals("qui est esse", response.title)},
            {assertEquals("est rerum tempore vitae\n" +
                    "sequi sint nihil reprehenderit dolor beatae ea dolores neque\n" +
                    "fugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\n" +
                    "qui aperiam non debitis possimus qui neque nisi nulla", response.body)}
        )
        //println(response)
    }
}

class Case12{
    @Test
    fun `Extract every post and assert its length of 100`(){
        val response:List<Post> = RestAssured.given()
            .baseUri(baseUrl)
            .`when`()
            .get("/posts")
            .then()
            .statusCode(200)
            .extract()
            .`as`(Array<Post>::class.java)
            .toList()
        assertEquals(100,response.size)
    }
}
