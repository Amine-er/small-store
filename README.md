# Small Store
A simple e-commerce app to learn advanced topics in microservices distributed architecture.

## Introduction
-  This project is a development of a small set of **Spring Boot** and **Cloud** based Microservices projects that implement Microservices architecture and design patterns, and coding best practices.
  
-  This project uses cutting edge technologies : 
     -  Development Java 17 and Springboot 3 and gradle for build
     -  Spring cloud gateway as an API Gateway
     -  Keycloak for authentication and authorization
     -  Eureka as a discovery service
     -  Spring config server and profile for config management
     -  Micrometer and zipkin for distributed tracing
     -  Springboot admin for monitoring
     -  Rest and GRPC for synchron communication between microservices
     -  Database migration with flyway and postgres
     -  Reactive programming with webflux spring data reactive and mongodb
     -  Deployment docker and docker-compose
---

## Getting started
### System components Structure
Let's explain first the system structure to understand its components:
```
Small-store μService --> Parent folder. 
|- infrastructures
  |- config-server --> Service discovery server
  |- eureka-server --> Centralized Configuration server
|-modules
  |- api-gateway --> API Gateway server
  |- customer-service --> Customer management service
  |- product-service --> Product service
  |- review-service --> Review service
|-api-common 
  |- grpc-common --> grpc shared contract proto 
|- docker-compose.yml --> contains all services
```
![image](https://github.com/user-attachments/assets/f1b95421-eac8-4f25-a86d-124ebd3e0841)

Now, as we have learned about different system components, then let's start.

### Running Them All
Now it's the time to run all of our  Microservices, and it's straightforward just run the following `docker-compose` commands:
```
docker-compose up
```
![docker](https://github.com/user-attachments/assets/c047928f-85eb-4f9e-aeb1-3dd57d26de17)

### Access Service Discovery Server (Eureka)
If you would like to access the Eureka service discovery point to this URL [http://localhosts:8443/eureka/web](https://localhost:8443/eureka/web) to see all the services registered inside it. 
![eureka](https://github.com/user-attachments/assets/bc8b72eb-4870-4060-a52c-e28cf7f16c64)


### Access Zipkin
You can manually check traceId flow between microservices using zipkin UI at this URL [http://localhosts:9411/zipkin](https://localhost:9411/zipkin)
![zipkin2](https://github.com/user-attachments/assets/9442954f-5564-4ddd-86ce-fc65b6da832c)

### Access Spring boot admin
You can check the dashboard of spring admin for metrics and logs at this URL [http://localhosts:7070/admin](https://localhost:7070/admin)
![admin](https://github.com/user-attachments/assets/b4b66032-2441-4b16-aeb0-07489c741c95)

