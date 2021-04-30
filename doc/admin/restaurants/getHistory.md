# Show Restaurant Vote And Menu History
____

Show all stored Restaurants.

**URL :** `/admin/restaurants/history`

**Method :** `GET`

**Auth required :** YES

**Permissions required :** None

**Data constraints :** `{}`

### Success Response
____

**Condition :** No available Restaurants in history.

**Code :** `200 OK`

**Content :** `{[]}`

### OR

**Condition :** One or more available Restaurants stored in history.

**Code :** `200 OK`

**Content :** In this example, the Admin can see three
Restaurants with their menu, votes and stored date as Absurd Bird,
Five Guys and Honi Poke:

```
[
      {
      "id": 100012,
      "name": "Absurd Bird",
      "votes": 2,
      "saved": "2021-04-30T22:32:42.233+00:00",
      "menu":       [
                  {
            "name": "Fanta",
            "price": 1.99
         },
                  {
            "name": "Chips",
            "price": 3.85
         },
                  {
            "name": "Chocolate Cake",
            "price": 5.1
         },
                  {
            "name": "Big Roaster",
            "price": 11.99
         }
      ]
   },
      {
      "id": 100013,
      "name": "Five Guys",
      "votes": 0,
      "saved": "2021-04-30T22:33:17.396+00:00",
      "menu":       [
                  {
            "name": "Bacon Burger",
            "price": 10.99
         },
                  {
            "name": "Chicken Nugets",
            "price": 3.5
         },
                  {
            "name": "Coca-Cola",
            "price": 1.99
         },
                  {
            "name": "Sprite",
            "price": 1.99
         },
                  {
            "name": "Chicken Burger",
            "price": 12.99
         }
      ]
   },
      {
      "id": 100014,
      "name": "Honi Poke",
      "votes": 1,
      "saved": "2021-04-30T22:33:19.703+00:00",
      "menu":       [
                  {
            "name": "Avocado Poke",
            "price": 5.99
         },
                  {
            "name": "Salmon Poke",
            "price": 5.99
         },
                  {
            "name": "Ginger Shot",
            "price": 5.99
         }
      ]
   }
]
```
