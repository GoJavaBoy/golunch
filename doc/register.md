# Registration
____

Used to create new user.

**URL :** `/profile/register/`

**Method :** `POST`

**Auth required :** NO

**Data constraints**

```
{
"name": "[2 to 30 chars]"
"email": "[1 to 30 chars]",
"password": "[5 to 32 chars]"
}
```

**Data example**

```
{
"name" : "Jhon"
"email" : "user@gmail.com",
"password" : "userjhon123"
}
```

### Success Response
____

**Condition :** Data provided is valid

**Code :** `201 CREATED`

**Content example :** Response will reflect back the created user information.

```
{
"id": 100012,
"name": "newName",
"email": "newemail@ya.ru",
"password": "{bcrypt}$2a$10$jsZgK1dPbeL4AIw9.Cgmh",
"registered": "2021-04-29T22:55:09.105+00:00",
"roles": ["USER"]
}
```

### Error Response
____

**Condition :** If 'name' or 'email' or 'password' is 
in invalid format.

**Code :** `422 UNPROCESSABLE ENTITY`

**Content :**

```
{
"url" : "/profile/register",
"message" : "Validation failed for..."
}
```