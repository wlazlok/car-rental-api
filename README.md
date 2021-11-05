# Car Rental API
This project is part of my engineer's thesis, the API created with Spring for my Car Rental App.

The architecture of the project consists of microservices that communicate with each other.

### Used technologies:
* Java (17.0.0)
* Spring Boot (2.5.4)
* Spring Cloud (Hoxton.SR12)
* Spring Security + JWT (2.5.4)
* Spring Data, Spring Web
* Thymeleaf (email templates) (2.4.4)
* Lombok (1.18.20)
* JUnit5 + Mockito
* MySQL (locally)

### Microservices:
* ds-gateway
* ds-admin
* ds-react
* ds-read
* ds-update

### ds-gateway
Gateway - entry point responsible for distributing all requests between ds-admin and ds-react.
### ds-admin
Microservice responsible for handling admin requests e.g. edit/create product, user management.
### ds-react
Microservice responsible for handling user requests, fetching data. Forwards the request to ds-read/ds-update
depending on the URL.
### ds-read
Microservice responsible for reading data from database.
### ds-update
Microservice responsible for updating data in the database.
### ds-model
A module containing s all object, entities used in project.
### ds-api-commons
A module containing classes which can be used in each module incl. repositories, feign clients, 
error handlers, configuration.