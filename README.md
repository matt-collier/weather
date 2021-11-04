# Task One - Weather API

Connect to the OpenWeather API found here (https://openweathermap.org/api/one-call-api).

You can use one of the following API keys.
If none of these work you can register for free with your email address
3f159511ac64f5393feaa6f3c700c74b
91a5e3d94708c57e8248a454d817a493
F8bbd2c4154b67e214403406996bbf44
44d972716a576f33e58ba3112e9a3cdd

Your task is to connect to the forecast API above, to parse the response and display a result.

Allow the end user to specify a given latitude and longitude. The parameters can be specified
via a web form, RESTful interface or other method you deem appropriate to receive the user
input.

[Inshur lat/long: 50.824955973889, -0.13878781625840952]

For the given lat / long, parse the API data and find the warmest day over the next 7 days. If
multiple days have the same highest temperature, then go for the day that has the warmest
temperature and the lowest humidity.

If multiple days have both the warmest temperature and lowest humidity, then display the first of
those days.

The output can be textual or graphical, html or json as you see fit. There is no right or wrong
way to approach this.

You should be able to show that your code would work under different values returned from the
API

#To Run

`mvn spring-boot:run`


Example GET request

`http://localhost:8080/warmest-day?longitude=-0.152778&latitude=50.827778`
