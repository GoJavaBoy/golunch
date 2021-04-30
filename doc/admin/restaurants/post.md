# Create New Restaurant
____

Used to create a new Restaurant.

**URL :** `/admin/restaurants/`

**Method :** `POST`

**Auth required :** YES

**Permissions required :** User Account should have Admin rights

**Data constraints**

```
{
"name": "[2 to 30 chars]"
"menu": [
    {
      "name": "[2 to 30 chars]",
      "price": [not null]
    },
    {
      "name": "[2 to 30 chars]",
      "price": [not null]
    }
    ...
  ]
}
```

**Data example** All fields must be sent.

```
{
  "name": "New Restaurant",
  "menu": [
    {
      "name": "New dish #3",
      "price": 1.99
    },
    {
      "name": "New dish #2",
      "price": 0.9
    },
    {
      "name": "New dish #1",
      "price": 5.35
    }
  ]
}
```

### Success Response
____

**Condition :** If everything is OK, data provided is valid,
and an Restaurant didn't exist.

**Code :** `201 CREATED`

**Content example :** Response will reflect back the created Restaurant information.

```
{
  "id": 100012,
  "name": "New Restaurant",
  "menu": [
    {
      "name": "New dish #3",
      "price": 1.99
    },
    {
      "name": "New dish #2",
      "price": 0.9
    },
    {
      "name": "New dish #1",
      "price": 5.35
    }
  ],
  "votes": 0
}
```

### Error Response
____

**Condition :** If Restaurant already exists.

**Code :** `422 UNPROCESSABLE ENTITY`

**Content :**

```
{
  "url": "/admin/restaurants/",
  "message": "integrity constraint violation:
   unique constraint or index violation;
    RESTAURANT_NAME_IDX table: RESTAURANTS"
}
```

### OR

**Condition :** If fields are missed or invalid, menu item has
invalid price or name.

**Code :** `422 UNPROCESSABLE ENTITY`

**Content :**

```
{
  "url": "/admin/restaurants/",
  "message": "Validation failed for..."
}
```