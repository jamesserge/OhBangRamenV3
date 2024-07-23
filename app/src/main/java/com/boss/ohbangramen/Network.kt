package com.boss.ohbangramen

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuNetwork(
    @SerialName("menu")
    val menu: List<MenuItemNetwork>,
) {
    @Serializable
    data class MenuItemNetwork(
        @SerialName("id")
        val id: Int,
        @SerialName("title")
        val title: String,
        @SerialName("description")
        val description: String,
        @SerialName("price")
        val price: String,
        @SerialName("image")
        val image: String,
        @SerialName("category")
        val category: String
    ) {
        fun toMenuItemRoom() = MenuItemRoom(
            id,
            title,
            description,
            price,
            image,
            category
        )
    }
}

//Below is for possible testing.
val jsonString = """
    {
      "menu": [
        {
          "id": 1,
          "title": "Greek Salad",
          "description": "The famous greek salad of crispy lettuce, peppers, olives, our Chicago.",
          "price": "10",
          "image": "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
          "category": "starters"
        },
        {
          "id": 2,
          "title": "Lemon Desert",
          "description": "Traditional homemade Italian Lemon Ricotta Cake.",
          "price": "10",
          "image": "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/lemonDessert%202.jpg?raw=true",
          "category": "desserts" 
        },
        {
          "id": 3,
          "title": "Grilled Fish",
          "description": "Our Bruschetta is made from grilled bread that has been smeared with garlic and seasoned with salt and olive oil.",
          "price": "10",
          "image": "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/grilledFish.jpg?raw=true",
          "category": "mains"
        },
        {
          "id": 4,
          "title": "Pasta",
          "description": "Penne with fried aubergines, cherry tomatoes, tomato sauce, fresh chilli, garlic, basil & salted ricotta cheese.",
          "price": "10",
          "image": "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/pasta.jpg?raw=true",
          "category": "mains"
        },
        {
          "id": 5,
          "title": "Bruschetta",
          "description": "Oven-baked bruschetta stuffed with tomatos and herbs.",
          "price": "10",
          "image": "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/bruschetta.jpg?raw=true",
          "category": "starters"
        }
      ]
    }
"""