Dave Lowry - Target Interview Case Study


There are two ways to run this application - one with docker and one without.

Docker:
1. _./mvnw clean; ./mvnw install -P docker_
2. _docker-compose up_

Non-Docker Through IDE
1. _./mvnw clean; ./mvnw install_
2. Run CasestudyApplication.java


Regardless of how you start the app you can utilize SwaggerUI to test the endpoints
http://localhost:8080/swagger-ui/#/

The endpoints utilized are 
Get: http://localhost:8080/products/13860428
Post: http://localhost:8080/products/13860428
     - Include a Request Body in the form of:
    {
        "current_price": {
            "currency_code": "string",
            "value": 0
        },
        "id": "string",
        "name": "string"
    }