# GoLunch - lunch of the day


## Overview
____
GoLunch is voting system for deciding where to have lunch.

- 2 types of users: admin and regular users 
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a menuItem name and price)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
  - If it is before 11:00 we assume that he changed his mind.
  - If it is after 11:00 then it is too late, vote can't be changed

The GoLunch API is organized around REST and has
predictable resource-oriented URLs,
accepts JSON-encoded request bodies and returns
JSON-encoded responses, uses standard HTTP response codes,
authentication, and verbs.
____

Use Swagger UI to test it **_/swagger-ui.html_**

_Credentials for testing:_
- admin@gmail.com - admin
- user@yandex.ru - password
- user1@yandex.ru - password
- user2@yandex.ru - password
- user3@yandex.ru - password
- user4@yandex.ru - password
- user5@yandex.ru - password




