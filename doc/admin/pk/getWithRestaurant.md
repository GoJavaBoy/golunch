# Show An Account With Related Restaurant
____

Show a single Account with related Restaurant
if current User has access permissions to it.

**URL :** `/admin/users/:pk/with-restaurant`

**Method :** `GET`

**Auth required :** YES

**Permissions required :** User Account should have Admin rights

**Data :** `{}`

### Success Response
____

**Condition :** If Account exists and
Authorized User has required permissions.

**Code :** `200 OK`

**Content :**

For a User with ID 100005 on the local database.

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
### Error Response
____

**Condition :** If Account does not exist
with `id` of provided `pk` parameter.

**Code :** `422 UNPROCESSABLE ENTITY`

**Content :**

```
{
  "url": "/admin/users/:pk/",
  "message": "Not found entity with id=:pk"
}
```

### OR

**Condition :** If Account exists but
Authorized User does not have required permissions.

**Code :** `403 FORBIDDEN`

**Content :**

```
Forbidden
```
### Notes
____

Restaurant include in response only if not empty.