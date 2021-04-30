# Show Current User With Related Restaurant
____

Get the details of the currently Authenticated User along with basic information and related restaurant.

**URL :** /profile/with-restaurant/

**Method :** GET

**Auth required :** YES

**Permissions required :** None

### Success Response
____

**Code :** `200 OK`

**Content example**

For a User with ID 100001 on the local database.

```
{
  "id": 100005,
  "name": "User",
  "email": "user@yandex.ru",
  "password": "{noop}password",
  "registered": "2021-04-30T00:04:41.677+00:00",
  "restaurant": {
    "id": 100001,
    "name": "Absurd Bird",
    "menu": [
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
    ],
    "votes": 2
  },
  "roles": [
    "USER"
  ]
}
```
### Notes
____

Restaurant include in response only if not empty.