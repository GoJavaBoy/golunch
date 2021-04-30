# Delete User's Account
____

Allow the Authenticated User with right permission
delete User.

**URL :** `/admin/users/:pk/`

**URL Parameters :** `pk=[integer]` where `pk` is the ID of 
the User in the database.

**Method :** `DELETE`

**Auth required :** YES

**Permissions required :** User Account should have Admin rights

**Data :** `{}`

### Success Response
____

**Condition :** If the Account exists.

**Code :** `204 NO CONTENT`

**Content :** `{}`

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
