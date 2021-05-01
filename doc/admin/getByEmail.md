# Show Account By Email
____

Show Account by email if current User has access permissions to it.

**URL :** `/admin/users/by?email=:em`

**URL Parameters :** `email=[string]` where `em` is the User `email` on the server.

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

For a User with Email `user@yandex.ru` on the local database.

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
with `email` of provided `em` parameter.

**Code :** `422 UNPROCESSABLE ENTITY`

**Content :**

```
{
  "url": "/admin/users/by?email=:em",
  "message": "Not found entity with id=:em"
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