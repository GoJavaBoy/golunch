# Show Accessible Accounts
____

Show all Accounts the active User can access
and with what permission level.

**URL :** `/admin/users/`

**Method :** `GET`

**Auth required :** YES

**Permissions required :** User Account should have Admin rights

**Data constraints :** `{}`

### Success Response
____

**Condition :** No available Accounts to display.

**Code :** `200 OK`

**Content :** `{[]}`

### OR

**Condition :** User can see one or more Accounts.

**Code :** `200 OK`

**Content :**  In this example, User can
see three Accounts
as User, Admin, User1:

```
[
  {
    "id": 100005,
    "name": "User",
    "email": "user@yandex.ru",
    "password": "{noop}password",
    "registered": "2021-04-30T09:44:14.615+00:00",
    "roles": [
      "USER"
    ]
  },
  {
    "id": 100006,
    "name": "Admin",
    "email": "admin@gmail.com",
    "password": "{noop}admin",
    "registered": "2021-04-30T09:44:14.615+00:00",
    "roles": [
      "ADMIN",
      "USER"
    ]
  },
  {
    "id": 100007,
    "name": "User1",
    "email": "user1@yandex.ru",
    "password": "{noop}password",
    "registered": "2021-04-30T09:44:14.615+00:00",
    "roles": [
      "USER"
    ]
  }
]
```
