package com.example.ohbangramen

//import io.ktor.client.HttpClient
//import io.ktor.client.call.receive
//import io.ktor.client.request.get
//import io.ktor.client.statement.HttpResponse
//import io.ktor.http.ContentType
//import io.ktor.http.contentLength
//import io.ktor.http.contentType
//import kotlinx.coroutines.runBlocking
//
//fun main() = runBlocking {
//    // Initialize HTTP client
//    val client = HttpClient()
//
//    // Make a GET request and retrieve the response
//    val response: HttpResponse = client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
//
//    // Check if the response has a content length (indicating there's some content)
//    val contentLength = response.contentLength()
//
//    // Check if the response has a content type and if it's JSON
//    val isJsonResponse = response.contentType()?.match(ContentType.Application.Json) ?: false
//
//    // Output the result
//    if (contentLength != null && contentLength > 0) {
//        if (isJsonResponse) {
//            println("Response is JSON.")
//        } else {
//            println("Response is not JSON.")
//        }
//    } else {
//        println("Empty response or unable to determine content.")
//    }
//
//    // Close the client
//    client.close()
//}
//
////package com.example.littlelemon
////
////import io.ktor.client.HttpClient
////import io.ktor.client.call.body
////import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
////import io.ktor.client.request.get
////import io.ktor.serialization.kotlinx.json.json
////import kotlinx.coroutines.runBlocking
////import kotlinx.serialization.Serializable
////import kotlinx.serialization.json.Json
////
////@Serializable
////data class MenuItem(
////    val id: Int,
////    val title: String,
////    val description: String,
////    val price: String,
////    val image: String,
////    val category: String
////)
////
////fun main() = runBlocking {
////    val client = HttpClient {
////        install(ContentNegotiation) {
////            json(Json { ignoreUnknownKeys = true })
////        }
////    }
////
////    // Initial test: Retrieve and print JSON response as String
////    val jsonResponse: String = client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json").body()
////    println("Initial response: $jsonResponse")
////
////    //Second test: Deserialize JSON directly into MenuItem
//////    val menuItemResponse: MenuItem = client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/sample_item.json")
//////    println("Parsed menu item: $menuItemResponse")
////}