# Show Single Restaurant
____

Show a single Restaurant if it's available
for the current User.

**URL :** `/profile/restaurants/:pk/`

**URL Parameters :** 
`pk=[integer]` where `pk` is the ID of the Restaurant on the server.

**Method :** `GET`

**Auth required :** YES

**Permissions required :** None

**Data :** `{}`

### Success Response
____

**Condition :** If Restaurant exists.

**Code :** `200 OK`

**Content example**

For a Restaurant with ID 100000 on the local database.

```
 {
    "id": 100000,
    "name": "Five Guys",
    "menu": [
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
    ],
    "votes": 0
  }
```
### Error Response
____

**Condition :** If Restaurant does not
exist with `id` of provided `pk` parameter.

**Code :** `422 UNPROCESSABLE ENTITY`

**Content :**

```
{
"url": "/profile/restaurant/:pk",
"message": "Not found entity with id=:pk"
}