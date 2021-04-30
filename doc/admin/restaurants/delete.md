# Delete Restaurant
____

Allow the Authenticated User with right permission
delete Restaurant.

**URL :** `/admin/restaurants/:pk/`

**URL Parameters :** `pk=[integer]` where `pk` is the ID of
the Restaurant in the database.

**Method :** `DELETE`

**Auth required :** YES

**Permissions required :** User Account should have Admin rights

**Data :** `{}`

### Success Response
____

**Condition :** If the Restaurant exists.

**Code :** `204 NO CONTENT`

**Content :** `{}`

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
