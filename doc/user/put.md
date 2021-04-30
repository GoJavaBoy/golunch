# Update Current User
____

Allow the Authenticated User update their details.

**URL :** `/profile/`

**Method :** `PUT`

**Auth required :** YES

**Permissions required :** None

**Data constraints**

```
{
"name": "[1 to 30 chars]"
"email": "[1 to 30 chars]",
"password": "[5 to 100 chars]"
}
```

**Data example**

Partial data is not allowed.

```
{
"name": "Jhon Updated"
"email": "userUpdated@gmail.com",
"password": "userjhonupdate123"
}
```

### Success Response
____

**Condition :** Data provided is valid and User is Authenticated.

**Code :** `204 NO CONTENT`

### Error Response
____

**Condition :** If provided data is invalid,
e.g. a name field is too long.

**Code :** `422 UNPROCESSABLE ENTITY`

**Content :**

```
{
"url": "/profile/",
"message": "Validation failed for..."
}