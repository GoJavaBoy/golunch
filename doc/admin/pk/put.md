# Update User
____

Allow the Authenticated User with right permission
update User details.

**URL :** `/admin/users/:pk`

**Method :** `PUT`

**Auth required :** YES

**Permissions required :** User Account should have Admin rights

**Data constraints**

```
{
"name": "[1 to 30 chars]"
"email": "[email format from 1 to 30 chars]",
"password": "[5 to 100 chars]"
"roles": [
    [Role.class...]
  ]
}
```

**Data example**

Partial data is not allowed.

```
{
  "name": "Updated User",
  "email": "updateduser@gmail.com",
  "password": "updatedpass",
  "roles": [
    "USER"
  ]
}
```

### Success Response
____

**Condition :** Data provided is valid, User is Authenticated and has
admin rights.

**Code :** `200 OK`

### Error Response
____

**Condition :** Account does not exist at URL

**Code :** `422 UNPROCESSABLE ENTITY`

**Content :**

```
{
"url": "/admin/users/:pk",
"message": "... must be with id=:pk "
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
### OR

**Condition :** If provided data is invalid,
e.g. a name field is too long.

**Code :** `422 UNPROCESSABLE ENTITY`

**Content :**

```
{
"url": "/admin/users/:pk",
"message": "Validation failed for..."
}