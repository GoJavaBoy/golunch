# Show Single Account
____

Show a single Account if current User has access permissions to it.

**URL :** `/admin/users/:pk/`

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

For a User with ID 100001 on the local database.

```
{
"id": 100001,
"name": "User",
"email": "user@yandex.ru",
"password": "{noop}password",
"registered": "2021-04-29T22:55:09.105+00:00",
"roles": ["USER"]
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