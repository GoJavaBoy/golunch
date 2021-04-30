# GoLunch - lunch of the day


## Overview
____
GoLunch is voting system for deciding where to have lunch.

- 2 types of users: admin and regular users 
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
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

## API Reference
____
### Open Endpoints 

Open endpoints require no Authentication.

- [Registration]() : POST /register

### Endpoints that require Authentication

Closed endpoints require a valid credentials for
basic authentication. A password and email can be
acquired from the REST API testing tool authentication form.

#### Current User related

Each endpoint manipulates or displays information related
to the User whose credentials is provided with the request:

_Regular user endpoints:_

- Show Account : GET /profile/
- Show Account With Related Restaurant : GET /profile/with-restaurant/
- Update Account : PUT /profile/
- Delete An Account : DELETE /profile/

_Restaurant endpoints:_

- Show Accessible Restaurants : GET /profile/restaurants/
- Show An Restaurant : GET /profile/restaurants/:pk/
- Vote For Restaurant : PATCH /profile/restaurants/:pk/vote/

#### Account related

Endpoints for viewing and manipulating the Accounts
that the Authenticated User has admin permissions.

_Admin endpoints:_

- Show Accessible Accounts : GET /admin/users/
- Create Account : POST /admin/users/
- Show An Account By ID : GET /admin/users/:pk/
- Show An Account By Email : GET /admin/users?email=:email
- Show An Account With Related Restaurant : GET /admin/users/:pk/with-restaurant/
- Update An Account : PUT /admin/users/:pk/
- Delete An Account : DELETE /admin/users/:pk/

_Restaurant endpoints:_

- Show Accessible Restaurants : GET /admin/restaurants/
- Show An Restaurant : GET /admin/restaurants/:pk/
- Vote For Restaurant : PATCH /admin/restaurants/:pk/vote/
- Update Restaurant : PUT /admin/restaurants/:pk/
- Create Restaurant : POST /admin/restaurants/
- Delete Restaurant : DELETE /admin/restaurants/:pk/
- Show Restaurant History : GET /admin/restaurants/history/

### Responses
Many API endpoints return the JSON representation of
the resources created or edited.
However, if an invalid request is submitted,
or some other error occurs, GoLunch returns a JSON
response in the following format:
```
{
"url" : string,
"message" : string
}
```

>The `url` attribute contains a endpoint where error came from.
>
>The `message` attribute contains a root Exception message.

### Status Codes

GoLunch returns the following status codes in its API:

| Status Code | Description |
|----------------|:---------:|
| 200 | `OK` | 
| 201 | `CREATED` | 
| 422 | `UNPROCESSABLE ENTITY` |
| 404 | `NOT FOUND` |
| 409 | `CONFLICT` | 
| 401 | `UNAUTHORIZED` |
| 500 | `INTERNAL SERVER ERROR` | 

### cURL samples for API testing

> For windows use `Git Bash`

#### get All Users
`curl -s http://localhost:8080/golunch/admin/users --user admin@gmail.com:admin`

#### register Users
`curl -s -i -X POST -d '{"name":"New User","email":"test@mail.ru","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/golunch/profile/register`

#### get Profile
`curl -s http://localhost:8080/golunch/profile --user user@yandex.ru:password`

#### get All Restaurants
`curl -s http://localhost:8080/golunch/profile/restaurants --user user@yandex.ru:password`

#### get Restaurant 100001
`curl -s http://localhost:8080/golunch/profile/restaurants/100001  --user user@yandex.ru:password`

#### vote for Restaurant 100001
`curl -s -X PATCH "http://localhost:8080/golunch/profile/restaurants/100001/vote" --user user3@yandex.ru:password`

#### get Restaurant not found
`curl -s -v http://localhost:8080/golunch/profile/restaurants/10 --user user@yandex.ru:password`

#### get Restaurants History
`curl -s http://localhost:8080/golunch/admin/restaurants/history --user admin@gmail.com:admin`

#### delete Restaurant
`curl -s -X DELETE http://localhost:8080/golunch/admin/restaurants/100001 --user admin@gmail.com:admin`

#### create Restaurant
`curl -s -X POST -d '{"name":"New Restaurant","menu":[{"name":"New dish #2","price":0.90},{"name":"New dish #1","price":5.35},{"name":"New dish #3","price":1.99}]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/golunch/admin/restaurants --user admin@gmail.com:admin`



