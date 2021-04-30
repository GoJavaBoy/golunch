# Update Restaurant
____

Allow the Authenticated User with right permission
update Restaurant `name` or `menu`.

**URL :** `/admin/restaurants/:pk`

**Method :** `PUT`

**Auth required :** YES

**Permissions required :** User Account should have Admin rights

**Data constraints**

```
{
"name": "[2 to 30 chars]"
"menu": [
    {
      "name": "[2 to 30 chars]",
      "price": [not null]
    },
    {
      "name": "[2 to 30 chars]",
      "price": [not null]
    }
    ...
  ]
}
```

**Data example** All fields must be sent. 
Partial data is not allowed.


```
{
  "name": "Updated Restaurant",
  "menu": [
    {
      "name": "Updated dish #3",
      "price": 0.99
    },
    {
      "name": "Updated dish #2",
      "price": 1.9
    },
    {
      "name": "Updated dish #1",
      "price": 4.35
    }
  ]
}
```

### Success Response
____

**Condition :** Data provided is valid, User is Authenticated and has
admin rights.

**Code :** `204 NO CONTENT`

### Error Response
____

**Condition :** Restaurant does not exist at URL

**Code :** `422 UNPROCESSABLE ENTITY`

**Content :**

```
{
"url": "/admin/restaurants/:pk",
"message": "... must be with id=:pk "
}
```

### OR

**Condition :** If Restaurant exists but
Authorized User does not have required permissions.

**Code :** `403 FORBIDDEN`

**Content :**

```
Forbidden
```
### OR

**Condition :** If provided data is invalid,
e.g. a name field is too long or menu item price is null.

**Code :** `422 UNPROCESSABLE ENTITY`

**Content :**

```
{
"url": "/admin/restaurants/:pk",
"message": "Validation failed for..."
}