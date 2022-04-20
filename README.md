# Order Service
## Base vertx with Spring Boot

#### Daniel Machado Vasconcelos
### Basic requirements (that were implemented):
* Use Dependency Injection and Inversion of Control;
* Use Flyway to configure the database tables;
* Make sure to log all the request to the applications;

### Extra requirements to be done:
* ~~Replace the console logs for log4j~~
* ~~Include Postgres database integration~~
* Expose metrics and health checks
* Provide authentication and authorisation
* ~~Replace the JsonObjects for Data Models~~
* Make sure to log `correlation_id` and `event_id`

---
Prerequisites
-------------

* Java JDK 17
* Gradle
* Docker / Docker Compose

#### Resources
* Postgres

### Tech Stack usage

**Postgres**

Simple SQL database to persist the data. It can be flexible to use json data in a event source style

## How to build?

Start the external resources by running the docker-compose file. (It may take a while to start all resources)
```bash
docker-compose up -d 
```

Start the service that expose the REST API by running the class: `Application.java`


####Use the following commands to place an order and fetch orders by id :

### To place an order
```bash
curl --location --request POST 'localhost:8080/orders' \
--header 'Content-Type: application/json' \
--data-raw '{
    "order_id": "123456789",
    "amount": 4500
}'
```
### To get the order
```bash
curl --location --request GET 'localhost:8080/orders/{order_id}' \
--header 'Content-Type: application/json' \

```