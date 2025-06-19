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
