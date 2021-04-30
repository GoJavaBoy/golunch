# Show Current User
____

Get the details of the currently Authenticated User along with
basic information.

**URL :** `/profile/`

**Method :** `GET`

**Auth required :** YES

**Permissions required :** None

### Success Response
____

**Code :** `200 OK`

**Content example**

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
