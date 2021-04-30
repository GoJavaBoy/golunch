# Show Accessible Restaurants
____

Show all Restaurants the current User can vote.

**URL :** `/profile/restaurants/`

**Method :** `GET`

**Auth required :** YES

**Permissions required :** None

**Data constraints :** `{}`

### Success Response
____

**Condition :** No available Restaurants for vote.

**Code :** `200 OK`

**Content :** `{[]}`

### OR

**Condition :** One or more available Restaurant for vote.

**Code :** `200 OK`

**Content :** In this example, the User can see three
Restaurants with their menu and votes as Five Guys, Absurd Bird, Honi Poke:

```
[
  {
    "id": 100000,
    "name": "Five Guys",
    "menu": [
      {
        "name": "Bacon Burger",
        "price": 10.99
      },
      {
        "name": "Chicken Nugets",
        "price": 3.5
      },
      {
        "name": "Coca-Cola",
        "price": 1.99
      },
      {
        "name": "Sprite",
        "price": 1.99
      },
      {
        "name": "Chicken Burger",
        "price": 12.99
      }
    ],
    "votes": 0
  },
  {
    "id": 100001,
    "name": "Absurd Bird",
    "menu": [
      {
        "name": "Fanta",
        "price": 1.99
      },
      {
        "name": "Chips",
        "price": 3.85
      },
      {
        "name": "Chocolate Cake",
        "price": 5.1
      },
      {
        "name": "Big Roaster",
        "price": 11.99
      }
    ],
    "votes": 2
  },
  {
    "id": 100002,
    "name": "Honi Poke",
    "menu": [
      {
        "name": "Avocado Poke",
        "price": 5.99
      },
      {
        "name": "Salmon Poke",
        "price": 5.99
      },
      {
        "name": "Ginger Shot",
        "price": 5.99
      }
    ],
    "votes": 1
  }
]
```
