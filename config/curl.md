### curl samples (application deployed in application context `topjava`).
> For windows use `Git Bash`

<br/>
<br/>


##Endpoints for admin

#### get All Restaurants
`curl --user admin@gmail.com:admin http://localhost:8080/restaraunt/rest/admin/restaurants`

#### create Restaurant
`curl --user admin@gmail.com:admin -X POST http://localhost:8080/restaraunt/rest/admin/restaurants?restaurantName=KFC`

#### update Restaurant 100002
`curl --user admin@gmail.com:admin -X PUT http://localhost:8080/restaraunt/rest/admin/restaurants/100002?restaurantName=KFC2`

#### delete Restaurant 100002
`curl --user admin@gmail.com:admin -X DELETE http://localhost:8080/restaraunt/rest/admin/restaurants/100002`

#### get History
`curl --user admin@gmail.com:admin http://localhost:8080/restaraunt/rest/admin/restaurants/history`

#### get History in date
`curl --user admin@gmail.com:admin http://localhost:8080/restaraunt/rest/admin/restaurants/history?date=2015-05-30`

#### get Restaurant 100005 with Dishes and Votes today
`curl --user admin@gmail.com:admin http://localhost:8080/restaraunt/rest/admin/restaurants/100005`

#### get Restaurant 100005 with Dishes and Votes in date
`curl --user admin@gmail.com:admin http://localhost:8080/restaraunt/rest/admin/restaurants/100005?date=2015-05-30`

#### create Dish for Restaurant 100002
`curl --user admin@gmail.com:admin  --data '{"id":null,"name":"Cake2","date":"2015-05-29","price":200,"restaurant":null}' -H "Content-Type: application/json" -X POST http://localhost:8080/restaraunt/rest/admin/restaurants/100002/dishes`

#### get Dish 100007
`curl --user admin@gmail.com:admin http://localhost:8080/restaraunt/rest/admin/restaurants/dishes/100007`

#### update Dish 100007 for Restaurant 100005
`curl --user admin@gmail.com:admin  --data '{"id":100007,"name":"dishUPD","date":"2015-05-29","price":120,"restaurant":null}' -H "Content-Type: application/json" -X PUT http://localhost:8080/restaraunt/rest/admin/restaurants/100005/dishes/100007`

#### delete Dish 100007 for Restaurant 100005
`curl --user admin@gmail.com:admin -X DELETE http://localhost:8080/restaraunt/rest/admin/restaurants/100005/dishes/100007`

##Endpoints for regular user

#### delete Dish 100007 for Restaurant 100005
`curl --user user@yandex.ru:password  http://localhost:8080/restaraunt/rest/user/restaurants/`
