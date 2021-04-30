# Create New User
____

Used to create a new User.

**URL :** `/admin/users/`

**Method :** `POST`

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

**Data example** All fields must be sent.

```
{
  "name": "New User",
  "email": "newuser@gmail.com",
  "password": "newpass",
  "roles": [
    "USER"
  ]
}
```

### Success Response
____

**Condition :** If everything is OK, data provided is valid,
and an Account didn't exist for this User.

**Code :** `201 CREATED`

**Content example :** Response will reflect back the created User information.

```
{
  "id": 100012,
  "name": "New",
  "email": "new@gmail.com",
  "password": "{bcrypt}$2a$10$y5X/zolbPTeKUq8YvmjS",
  "registered": "2021-04-30T14:30:48.885+00:00",
  "roles": [
    "USER"
  ]
}
```

### Error Response
____

**Condition :** If User already exists.

**Code :** `422 UNPROCESSABLE ENTITY`

**Content :**

```
{
  "url": "/admin/users/",
  "message": "integrity constraint violation:
   unique constraint or index violation: USERS_UNIQUE_EMAIL_IDX"
}
```

### OR

**Condition :** If fields are missed or invalid.

**Code :** `422 UNPROCESSABLE ENTITY`

**Content :**

```
{
  "url": "/admin/users/",
  "message": "Validation failed for..."
}
```
