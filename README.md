Dave Lowry - Target Interview Case Study


There are three ways to run this application - docker, via the jar, via IDE.

Docker - Run from Project Root
1. _./mvnw clean; ./mvnw install -P docker_
2. _docker-compose up_

Non-Docker Through IDE - From Project Root
1. _./mvnw clean; ./mvnw install_
2. Run CasestudyApplication.java

Non-Docker Through Command line - From Project Root
1. _./mvnw clean; ./mvnw install_
2. _ java -jar target/casestudy-0.0.1-SNAPSHOT.jar

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